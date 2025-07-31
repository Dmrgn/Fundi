package data_access;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import entity.User;
import entity.UserFactory;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * DAO for user data implemented using a Database to persist the data.
 */
public class DBUserDataAccessObject implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, User> accounts = new HashMap<>();
    private final Map<String, String> nameToId = new HashMap<>();

    public DBUserDataAccessObject(UserFactory userFactory) throws SQLException {
        String query = """
                SELECT * FROM users
                """;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                accounts.put(id, userFactory.create(username, password));
                nameToId.put(username, id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public void save(User user) {
        String id = this.save(user.getName(), user.getPassword());
        accounts.put(id, user);
        nameToId.put(user.getName(), id);
    }

    @Override
    public User get(String username) {
        if (!nameToId.containsKey(username)) {
            return null;
        }
        String id = nameToId.get(username);
        if (!accounts.containsKey(id)) {
            return null;
        }
        return accounts.get(id);
    }

    @Override
    public boolean existsByName(String username) {
        return nameToId.containsKey(username);
    }

    @Override
    public String getId(String username) {
        return nameToId.get(username);
    }

    public void remove(String username) {
        accounts.remove(nameToId.get(username));
        nameToId.remove(username);
        String query = """
                DELETE FROM users WHERE username = ?;
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}