package data_access;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import use_case.create.CreateDataAccessInterface;
import use_case.portfolioHub.PortfolioHubDataAccessInterface;

/**
 * DAO for portfolios data implemented using a Database to persist the data.
 */
public class DBPortfoliosDataAccessObject implements PortfolioHubDataAccessInterface, CreateDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, Map<String, String>> portfolios = new HashMap<>();
    private final Map<String, String> userToId = new HashMap<>();

    /**
     * Load the portfolio data into memory from SQL database
     * @throws SQLException If database connection fails
     */
    public DBPortfoliosDataAccessObject() throws SQLException {
        String query = """
                SELECT p.id, p.name, u.id, u.username FROM portfolios p
                JOIN users u ON p.user_id = u.id 
                """;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String userId = rs.getString(3);
                String username = rs.getString(4);
                userToId.put(username, userId);

                if (!portfolios.containsKey(userId)) {
                    portfolios.put(userId, new HashMap<>());
                    portfolios.get(userId).put(name, id);
                } else {
                    portfolios.get(userId).put(name, id);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Get the portfolio data
     * @param username the name to search at
     * @return A list of portfolio names
     */
    @Override
    public Map<String, String> getPortfolios(String username) {
        if (!portfolios.containsKey(userToId.get(username))) {
            portfolios.put(userToId.get(username), new HashMap<>());
        }
        return portfolios.get(userToId.get(username));
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
                INSERT INTO portfolios(name, user_id)
                VALUES (?, ?) 
                RETURNING id
                """;
        String portfolioId = "";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, portfolioName);
            pstmt.setString(2, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                portfolioId = rs.getString(1);
                if (!portfolios.containsKey(userId)) {
                    portfolios.put(userId, new HashMap<>());
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return portfolioId;
    }

    /**
     * Update the portfolio map
     * @param portfolioName the Name
     */
    @Override
    public void save(String portfolioName, String username) {
        String id = saveDB(portfolioName, username);
        String userId = userToId.get(username);
        if (!portfolios.containsKey(userId)) {
            portfolios.put(userId, new HashMap<>());
        }
        portfolios.get(userId).put(portfolioName, id);
    }

    /**
     *
     * @param portfolioName the portfolioName to look for
     * @param username the users name
     * @return
     */
    @Override
    public boolean existsByName(String portfolioName, String username) {
        String userId = userToId.get(username);
        if (!portfolios.containsKey(userId)) return false;

        Map<String, String> userPortfolios = portfolios.get(userId);
        return userPortfolios.containsKey(portfolioName);
    }

    /**
     * Get the Id of a user
     * @param username The username
     * @return The id of the user
     */
    @Override
    public String getId(String username) {
        return userToId.get(username);
    }

    /**
     * Delete the portfolio for the given user from the DAO
     * (For Testing Only)
     * @param portfolioName The portfolio name to delete
     * @param username The username to delete the portfolio at
     */
    public void remove(String portfolioName, String username) {
        portfolios.get(userToId.get(username)).remove(portfolioName);
        String userId = userToId.get(username);
        userToId.remove(username);
        String query = """
                DELETE FROM portfolios WHERE user_id = ? and name = ?;
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, portfolioName);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
