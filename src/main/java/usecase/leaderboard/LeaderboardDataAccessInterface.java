package usecase.leaderboard;

import java.util.List;

/**
 * Data access interface for the leaderboard use case.
 */
public interface LeaderboardDataAccessInterface {
    /**
     * Gets portfolio leaderboard data.
     * @return List of portfolio data (portfolioName, username, totalValue)
     */
    List<PortfolioLeaderboardData> getPortfolioLeaderboardData();
    
    /**
     * Data structure for portfolio leaderboard information.
     */
    class PortfolioLeaderboardData {
        private final String portfolioName;
        private final String username;
        private final double totalValue;
        
        public PortfolioLeaderboardData(String portfolioName, String username, double totalValue) {
            this.portfolioName = portfolioName;
            this.username = username;
            this.totalValue = totalValue;
        }
        
        public String getPortfolioName() { return portfolioName; }
        public String getUsername() { return username; }
        public double getTotalValue() { return totalValue; }
    }
}
