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
    private final Connection connection = DriverManager.getConnection("JDBC:sqlite:data/fundi.sqlite");
    private final Map<String, User> accounts = new HashMap<>();

    public DBUserDataAccessObject(UserFactory userFactory) throws SQLException {
        String query = """
                SELECT * FROM users
                """;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                accounts.put(username, userFactory.create(username, password));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void save(String username, String password) {
        String query = """
                INSERT INTO users(username, password)
                VALUES (?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getName(), user);
        this.save(user.getName(), user.getPassword());
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }
}