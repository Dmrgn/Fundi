package data_access;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import entity.User;
import entity.UserFactory;
import use_case.main.MainDataAccessInterface;

/**
 * DAO for user data implemented using a Database to persist the data.
 */
public class DBMainDataAccessObject implements MainDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("JDBC:sqlite:data/fundi.sqlite");
    private final Map<String, String> portfolios = new HashMap<>();

    public DBMainDataAccessObject() throws SQLException {
        String query = """
                SELECT (p.id, p.name) FROM portfolios p
                JOIN users u ON p.user_id = u.id 
                              WHERE u.username = ? 
                """;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                portfolios.put(name, id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Get the portfolio data
     *
     * @param username the name to search at
     * @return A list of portfolio names
     */
    @Override
    public Map<String, String> getPortfolios(String username) {
        return portfolios;
    }
}