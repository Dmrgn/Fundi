package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import usecase.create.CreateDataAccessInterface;
import usecase.delete_portfolio.DeletePortfolioDataAccessInterface;

/**
 * DAO for portfolios data implemented using a Database to persist the data.
 */
public class DBPortfoliosDataAccessObject implements CreateDataAccessInterface, DeletePortfolioDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, Map<String, String>> portfolios = new HashMap<>();
    private final Map<String, String> userToId = new HashMap<>();

    /**
     * Load the portfolio data into memory from SQL database.
     * 
     * @throws SQLException If database connection fails
     */
    public DBPortfoliosDataAccessObject() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(
                    "SELECT p.id, p.name, u.id, u.username FROM portfolios p JOIN users u ON p.user_id = u.id");
            while (rs.next()) {
                String id = rs.getString(1), name = rs.getString(2), userId = rs.getString(3),
                        username = rs.getString(4);
                userToId.put(username, userId);
                portfolios.computeIfAbsent(userId, k -> new HashMap<>()).put(name, id);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get the portfolio data.
     * 
     * @param username the name to search at
     * @return A list of portfolio names
     */
    @Override
    public Map<String, String> getPortfolios(String username) {
        String userId = userToId.get(username);
        return portfolios.computeIfAbsent(userId, k -> new HashMap<>());
    }

    private String saveDB(String portfolioName, String username) {
        String query = """
                SELECT id FROM users WHERE username = ?
                """;

        String userId = "";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userId = rs.getString(1);
                if (!userToId.containsKey(username)) {
                    userToId.put(username, userId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        query = """
                INSERT INTO portfolios(name, user_id, balance)
                VALUES (?, ?, 10000)
                RETURNING id
                """;
        String portfolioId = "";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, portfolioName);
            pstmt.setString(2, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                portfolios.computeIfAbsent(userId, k -> new HashMap<>()).put(portfolioName, rs.getString(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return portfolioId;
    }

    /**
     * Update the portfolio map.
     * 
     * @param portfolioName the Name
     */
    @Override
    public void save(String portfolioName, String username) {
        String userId = userToId.computeIfAbsent(username, u -> {
            try (PreparedStatement pstmt = connection.prepareStatement("SELECT id FROM users WHERE username = ?")) {
                pstmt.setString(1, u);
                ResultSet rs = pstmt.executeQuery();
                return rs.next() ? rs.getString(1) : "";
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return "";
            }
        });
        try (PreparedStatement pstmt = connection
                .prepareStatement("INSERT INTO portfolios(name, user_id) VALUES (?, ?) RETURNING id")) {
            pstmt.setString(1, portfolioName);
            pstmt.setString(2, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                portfolios.computeIfAbsent(userId, k -> new HashMap<>()).put(portfolioName, rs.getString(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Return whether a portfolio exists for a user.
     * 
     * @param portfolioName the portfolioName to look for
     * @param username      the users name
     * @return A boolean value
     */
    @Override
    public boolean existsByName(String portfolioName, String username) {
        String userId = userToId.get(username);
        return portfolios.containsKey(userId) && portfolios.get(userId).containsKey(portfolioName);
    }

    /**
     * Delete the portfolio for the given user from the DAO.
     * (For Testing Only)
     * 
     * @param portfolioName The portfolio name to delete
     * @param username      The username to delete the portfolio at
     */
    @Override
    public void remove(String portfolioName, String username) {
        String userId = userToId.get(username);
        portfolios.getOrDefault(userId, new HashMap<>()).remove(portfolioName);
        String portfolioId = null;
        try (PreparedStatement pstmt = connection
                .prepareStatement("SELECT id FROM portfolios WHERE user_id = ? AND name = ?")) {
            pstmt.setString(1, userId);
            pstmt.setString(2, portfolioName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                portfolioId = rs.getString("id");
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (portfolioId != null) {
            try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM holdings WHERE portfolio_id = ?")) {
                pstmt.setString(1, portfolioId);
                pstmt.executeUpdate();
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        try (PreparedStatement pstmt = connection
                .prepareStatement("DELETE FROM portfolios WHERE user_id = ? and name = ?")) {
            pstmt.setString(1, userId);
            pstmt.setString(2, portfolioName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // --- Balance helpers for reuse across use cases ---
    public static double fetchBalance(String portfolioId) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
                PreparedStatement ps = conn.prepareStatement("SELECT balance FROM portfolios WHERE id = ?")) {
            // Try integer binding first, fall back to string
            try {
                ps.setInt(1, Integer.parseInt(portfolioId.trim()));
            } catch (NumberFormatException nfe) {
                ps.setString(1, portfolioId);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        }
        catch (SQLException ex) {
            System.out.println("Error fetching portfolio balance: " + ex.getMessage());
        }
        return 0.0;
    }

    public static void updateBalance(String portfolioId, double newBalance) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
                PreparedStatement ps = conn.prepareStatement("UPDATE portfolios SET balance = ? WHERE id = ?")) {
            ps.setDouble(1, newBalance);
            // Try integer binding first, fall back to string
            try {
                ps.setInt(2, Integer.parseInt(portfolioId.trim()));
            } catch (NumberFormatException nfe) {
                ps.setString(2, portfolioId);
            }
            int rowsUpdated = ps.executeUpdate();
            System.out.println("Updated balance for portfolio " + portfolioId + " to " + newBalance
                    + " (rows affected: " + rowsUpdated + ")");
        } catch (SQLException e) {
            System.out.println("Error updating portfolio balance: " + e.getMessage());
        }
    }
}
