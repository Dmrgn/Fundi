package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * DAO for user data implemented using a Database to persist the data.
 */
public class DBUserDataAccessObject implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, User> accounts = new HashMap<>();
    private final Map<String, String> nameToId = new HashMap<>();

    /**
     * Load the user data into memory.
     * @throws SQLException If the SQL connection fails
     */
    public DBUserDataAccessObject() throws SQLException {
        String query = """
                SELECT * FROM users
                """;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                accounts.put(id, new User(username, password));
                nameToId.put(username, id);
            }
        }

        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }

    private String save(String username, String password) {
        String query = """
                INSERT INTO users(username, password)
                VALUES (?, ?) RETURNING id;
                """;
        String id = "";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getString("id");
            }
        }

        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return id;
    }

    /**
     * Save the user into the DAO.
     * @param user the user to save
     */
    @Override
    public void save(User user) {
        String id = this.save(user.getName(), user.getPassword());
        accounts.put(id, user);
        nameToId.put(user.getName(), id);
    }

    /**
     * Get the user corresponding with the username.
     * @param username the username to look up
     * @return The user object
     */
    @Override
    public User get(String username) {
        if (!nameToId.containsKey(username) || accounts.containsKey(nameToId.get(username))) {
            return null;
        }
        return accounts.get(nameToId.get(username));
    }

    /**
     * Check if a user exists in the database.
     * @param username the username to look for
     * @return True or false based on whether the username exists
     */
    @Override
    public boolean existsByName(String username) {
        return nameToId.containsKey(username);
    }

    /**
     * Remove the user from the DAO.
     * (For testing only)
     * @param username The username
     */
    public void remove(String username) {
        accounts.remove(nameToId.get(username));
        nameToId.remove(username);
        String query = """
                DELETE FROM users WHERE username = ?;
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();

        }

        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}