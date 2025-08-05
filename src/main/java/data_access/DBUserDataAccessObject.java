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

import javax.swing.*;

/**
 * DAO for user data implemented using a Database to persist the data.
 */
public class DBUserDataAccessObject implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, User> accounts = new HashMap<>();
    private final Map<String, String> nameToId = new HashMap<>();

    /**
     * Load the user data into memory.
     *
     * @throws SQLException If the SQL connection fails
     */
    public DBUserDataAccessObject() throws SQLException {
        // Initialize database schema if tables don't exist
        String schema = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT,
                    password TEXT
                );
                CREATE TABLE IF NOT EXISTS portfolios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    user_id INTEGER REFERENCES users
                );
                CREATE TABLE IF NOT EXISTS stocks (
                    name TEXT,
                    price REAL,
                    date DATE,
                    CONSTRAINT key PRIMARY KEY (name, date)
                );
                CREATE TABLE IF NOT EXISTS transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    portfolio_id INTEGER REFERENCES portfolios,
                    stock_name TEXT,
                    amount INTEGER,
                    date DATE,
                    price REAL
                );

                CREATE TABLE IF NOT EXISTS "holdings" 
                (
                    portfolio_id INTEGER NOT NULL
                        REFERENCES portfolios(id),
                    ticker TEXT NOT NULL,
                    quantity INTEGER NOT NULL DEFAULT 0,
                    PRIMARY KEY (portfolio_id, ticker),
                    CHECK (quantity >= 0)
                );
                """;

        try (Statement statement = connection.createStatement()) {
            // Execute schema creation
            String[] statements = schema.split(";");
            for (String stmt : statements) {
                String trimmed = stmt.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        } catch (SQLException exception) {
            System.out.println("Schema initialization error: " + exception.getMessage());
        }

        // Load existing user data into memory
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
     *
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
     *
     * @param username the username to look up
     * @return The user object
     */
    @Override
    public User get(String username) {
        if (!nameToId.containsKey(username) || !accounts.containsKey(nameToId.get(username))) {
            return null;
        }
        return accounts.get(nameToId.get(username));
    }

    @Override
    public void saveNewPassword(String username, String newPassword) {
            String query = "UPDATE users SET password = ? WHERE username = ?;";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, newPassword);
                stmt.setString(2, username);
                stmt.executeUpdate();

                if (nameToId.containsKey(username)) {
                    String id = nameToId.get(username);
                    User updatedUser = new User(username, newPassword);
                    accounts.put(id, updatedUser);
                }

                System.out.println("Password updated successfully for: " + username);
            } catch (SQLException e) {
                System.out.println("Error updating password: " + e.getMessage());
            }
        }



    /**
     * Check if a user exists in the database.
     *
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
     *
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