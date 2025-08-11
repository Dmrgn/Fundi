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
                WITH latest_prices AS (
                    SELECT name, MAX(date) AS latest_date
                    FROM stocks
                    GROUP BY name
                )
                SELECT
                    p.name AS portfolio_name,
                    u.username,
                    COALESCE(SUM(
                        CASE
                            WHEN s.price IS NOT NULL THEN h.quantity * s.price
                            ELSE 0
                        END
                    ), 0) AS total_value
                FROM portfolios p
                JOIN users u ON p.user_id = u.id
                LEFT JOIN holdings h ON p.id = h.portfolio_id
                LEFT JOIN latest_prices lp ON h.ticker = lp.name
                LEFT JOIN stocks s ON s.name = lp.name AND s.date = lp.latest_date
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
                        rs.getDouble("total_value")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching leaderboard data: " + e.getMessage());
        }

        return portfolioData;
    }
}
