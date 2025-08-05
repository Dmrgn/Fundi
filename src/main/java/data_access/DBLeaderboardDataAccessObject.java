package data_access;

import use_case.leaderboard.LeaderboardDataAccessInterface;
import java.sql.*;
import java.util.*;

/**
 * Data access object for leaderboard data using SQLite database.
 */
public class DBLeaderboardDataAccessObject implements LeaderboardDataAccessInterface {
    private final Connection connection;

    public DBLeaderboardDataAccessObject() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    }

    @Override
    public List<LeaderboardDataAccessInterface.PortfolioLeaderboardData> getPortfolioLeaderboardData() {
        List<LeaderboardDataAccessInterface.PortfolioLeaderboardData> portfolioData = new ArrayList<>();
        String sql = """
            SELECT p.name as portfolio_name, u.username, SUM(h.quantity * s.price) AS total_value
            FROM portfolios p
            JOIN users u ON p.user_id = u.id
            JOIN holdings h ON p.id = h.portfolio_id
            JOIN (
                SELECT name, MAX(date) AS latest_date
                FROM stocks
                GROUP BY name
            ) latest ON h.ticker = latest.name
            JOIN stocks s ON s.name = latest.name AND s.date = latest.latest_date
            WHERE h.quantity > 0
            GROUP BY p.id, p.name, u.username
            ORDER BY total_value DESC
            LIMIT 100
            """;
            
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                portfolioData.add(new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                    rs.getString("portfolio_name"),
                    rs.getString("username"), 
                    rs.getDouble("total_value")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching leaderboard data: " + e.getMessage());
        }
        
        return portfolioData;
    }
}
