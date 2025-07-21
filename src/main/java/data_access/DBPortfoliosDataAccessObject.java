package data_access;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import use_case.create.CreateDataAccessInterface;
import use_case.main.MainDataAccessInterface;

/**
 * DAO for user data implemented using a Database to persist the data.
 */
public class DBPortfoliosDataAccessObject implements MainDataAccessInterface, CreateDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("JDBC:sqlite:data/fundi.sqlite");
    private final Map<String, String> portfolios = new HashMap<>();

    public DBPortfoliosDataAccessObject() throws SQLException {
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

    // Way too big --> TODO REFACTOR!!
    private String saveDB(String portfolioName, String username) {
        String query = """ 
                SELECT (id) FROM users WHERE username = ?
                """;

        String user_id = "";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery(query);
            while (rs.next()) {
                user_id = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        query = """
                INSERT INTO portfolios(portfolio_name, user_id)
                VALUES (?, ?)
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, portfolioName);
            pstmt.setString(2, user_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        query = """
                SELECT (id) FROM portfolios WHERE portfolio_name = ?
                """;
        String id = "";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, portfolioName);
            ResultSet rs = pstmt.executeQuery(query);
            while (rs.next()) {
                id = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    /**
     * Update the portfolio map
     *
     * @param portfolioName the Name
     */
    @Override
    public void save(String portfolioName, String username) {
        String id = saveDB(portfolioName, username);
        portfolios.put(portfolioName, id);
    }

    /**
     * Checks if the given portfolio exists for the user.
     *
     * @param portfolioName the portfolioName to look for
     * @return true if a portfolio with this name exists; false otherwise
     */
    @Override
    public boolean existsByName(String portfolioName) {
        return portfolios.containsKey(portfolioName);
    }
}