package entity;

/**
 * Entity representing a single entry in the leaderboard.
 */
public class LeaderboardEntry {
    private final String portfolioName;
    private final String username;
    private final double totalValue;
    private final int rank;

    public LeaderboardEntry(String portfolioName, String username, double totalValue, int rank) {
        this.portfolioName = portfolioName;
        this.username = username;
        this.totalValue = totalValue;
        this.rank = rank;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public String getUsername() {
        return username;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public int getRank() {
        return rank;
    }
}
