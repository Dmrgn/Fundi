import java.sql.*;

/**
 * One-off migration tool to hash existing plaintext passwords in the users
 * table.
 *
 * Usage: run from project root with classpath including SQLite JDBC.
 * WARNING: Backup your database before running. Idempotent: skips rows that
 * already look hashed (64 hex chars).
 */
public class HashExistingUsers {
    private static boolean looksHashed(String s) {
        if (s == null || s.length() != 64)
            return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean hex = (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f');
            if (!hex)
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite")) {
            conn.setAutoCommit(false);
            try (PreparedStatement select = conn.prepareStatement("SELECT id, username, password FROM users");
                    PreparedStatement update = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?");) {
                ResultSet rs = select.executeQuery();
                int updated = 0;
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");

                    if (looksHashed(password)) {
                        System.out.println("Skip (already hashed): " + username);
                        continue;
                    }

                    String hashed = entity.PasswordHasher.hash(password);
                    update.setString(1, hashed);
                    update.setInt(2, id);
                    update.addBatch();
                    updated++;
                }
                update.executeBatch();
                conn.commit();
                System.out.println("Updated passwords: " + updated);
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            }
        }
    }
}
