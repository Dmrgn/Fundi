package data_access;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private final UserFactory userFactory;

    public DBUserDataAccessObject(UserFactory userFactory) throws SQLException {
        this.userFactory = userFactory;

        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                User user = userFactory.create(id, username, password);
                accounts.put(id, user);
                nameToId.put(username, id);
            }
        } catch (SQLException e) {
            System.out.println("Failed to load users: " + e.getMessage());
        }
    }

    /**
     * This is the public save method required by LoginUserDataAccessInterface
     */
    @Override
    public void save(String username, String password) {
        String id = UUID.randomUUID().toString();
        try {
            String query = "INSERT INTO users(id, username, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to insert user: " + e.getMessage());
        }
        User user = userFactory.create(id, username, password);
        accounts.put(id, user);
        nameToId.put(username, id);
    }

    /**
     * This is the save method used in SignupInteractor
     */
    @Override
    public void save(User user) {
        try {
            String query = "INSERT INTO users(id, username, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to insert user: " + e.getMessage());
        }
        accounts.put(user.getId(), user);
        nameToId.put(user.getName(), user.getId());
    }

    @Override
    public User get(String username) {
        if (!nameToId.containsKey(username)) return null;
        String id = nameToId.get(username);
        return accounts.getOrDefault(id, null);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        String updateSQL = "UPDATE users SET password = ? WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int updated = statement.executeUpdate();

            if (updated > 0) {
                String id = nameToId.get(username);
                User newUser = userFactory.create(id, username, newPassword);
                accounts.put(id, newUser);
            }
        } catch (SQLException e) {
            System.out.println("Failed to update password: " + e.getMessage());
        }
    }

    @Override
    public boolean existsByName(String username) {
        return nameToId.containsKey(username);
    }

    @Override
    public String getId(String username) {
        return nameToId.get(username);
    }
}
