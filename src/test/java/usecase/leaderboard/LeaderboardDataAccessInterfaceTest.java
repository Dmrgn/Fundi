package usecase.leaderboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardDataAccessInterfaceTest {

    @Test
    void portfolioLeaderboardData_Constructor_ShouldCreateValidInstance() {
        // Arrange
        String portfolioName = "Tech Portfolio";
        String username = "alice";
        double totalValue = 15000.50;

        // Act
        LeaderboardDataAccessInterface.PortfolioLeaderboardData data = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                portfolioName, username, totalValue);

        // Assert
        assertEquals(portfolioName, data.getPortfolioName());
        assertEquals(username, data.getUsername());
        assertEquals(totalValue, data.getTotalValue(), 0.01);
    }

    @Test
    void portfolioLeaderboardData_WithZeroValue_ShouldHandleCorrectly() {
        // Arrange
        String portfolioName = "Empty Portfolio";
        String username = "newuser";
        double totalValue = 0.0;

        // Act
        LeaderboardDataAccessInterface.PortfolioLeaderboardData data = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                portfolioName, username, totalValue);

        // Assert
        assertEquals(portfolioName, data.getPortfolioName());
        assertEquals(username, data.getUsername());
        assertEquals(0.0, data.getTotalValue(), 0.01);
    }

    @Test
    void portfolioLeaderboardData_WithNegativeValue_ShouldAcceptNegativeValues() {
        // Arrange (in case of losses or debt)
        String portfolioName = "Loss Portfolio";
        String username = "unluckyuser";
        double totalValue = -500.25;

        // Act
        LeaderboardDataAccessInterface.PortfolioLeaderboardData data = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                portfolioName, username, totalValue);

        // Assert
        assertEquals(portfolioName, data.getPortfolioName());
        assertEquals(username, data.getUsername());
        assertEquals(-500.25, data.getTotalValue(), 0.01);
    }

    @Test
    void portfolioLeaderboardData_WithLargeValue_ShouldHandleCorrectly() {
        // Arrange
        String portfolioName = "Whale Portfolio";
        String username = "bigplayer";
        double totalValue = 1_000_000.99;

        // Act
        LeaderboardDataAccessInterface.PortfolioLeaderboardData data = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                portfolioName, username, totalValue);

        // Assert
        assertEquals(portfolioName, data.getPortfolioName());
        assertEquals(username, data.getUsername());
        assertEquals(1_000_000.99, data.getTotalValue(), 0.01);
    }

    @Test
    void portfolioLeaderboardData_WithNullStrings_ShouldAcceptNullValues() {
        // Arrange
        String portfolioName = null;
        String username = null;
        double totalValue = 1000.0;

        // Act
        LeaderboardDataAccessInterface.PortfolioLeaderboardData data = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                portfolioName, username, totalValue);

        // Assert
        assertNull(data.getPortfolioName());
        assertNull(data.getUsername());
        assertEquals(1000.0, data.getTotalValue(), 0.01);
    }

    @Test
    void portfolioLeaderboardData_WithEmptyStrings_ShouldHandleEmptyValues() {
        // Arrange
        String portfolioName = "";
        String username = "";
        double totalValue = 2500.75;

        // Act
        LeaderboardDataAccessInterface.PortfolioLeaderboardData data = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                portfolioName, username, totalValue);

        // Assert
        assertEquals("", data.getPortfolioName());
        assertEquals("", data.getUsername());
        assertEquals(2500.75, data.getTotalValue(), 0.01);
    }

    @Test
    void portfolioLeaderboardData_WithSpecialCharacters_ShouldHandleCorrectly() {
        // Arrange
        String portfolioName = "Pörtfölio with spéciál chars & symbols!";
        String username = "üser_123@domain.com";
        double totalValue = 3333.33;

        // Act
        LeaderboardDataAccessInterface.PortfolioLeaderboardData data = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                portfolioName, username, totalValue);

        // Assert
        assertEquals("Pörtfölio with spéciál chars & symbols!", data.getPortfolioName());
        assertEquals("üser_123@domain.com", data.getUsername());
        assertEquals(3333.33, data.getTotalValue(), 0.01);
    }

    @Test
    void portfolioLeaderboardData_Getters_ShouldReturnSameValues() {
        // Arrange
        String originalPortfolioName = "Consistent Portfolio";
        String originalUsername = "consistentuser";
        double originalTotalValue = 7777.77;

        LeaderboardDataAccessInterface.PortfolioLeaderboardData data = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                originalPortfolioName, originalUsername, originalTotalValue);

        // Act & Assert
        assertEquals(originalPortfolioName, data.getPortfolioName());
        assertEquals(originalUsername, data.getUsername());
        assertEquals(originalTotalValue, data.getTotalValue(), 0.01);

        // Multiple calls should return the same values
        assertEquals(originalPortfolioName, data.getPortfolioName());
        assertEquals(originalUsername, data.getUsername());
        assertEquals(originalTotalValue, data.getTotalValue(), 0.01);
    }

    @Test
    void portfolioLeaderboardData_MultipleInstances_ShouldBeIndependent() {
        // Arrange & Act
        LeaderboardDataAccessInterface.PortfolioLeaderboardData data1 = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                "Portfolio 1", "user1", 1000.0);

        LeaderboardDataAccessInterface.PortfolioLeaderboardData data2 = new LeaderboardDataAccessInterface.PortfolioLeaderboardData(
                "Portfolio 2", "user2", 2000.0);

        // Assert
        assertNotSame(data1, data2);
        assertNotEquals(data1.getPortfolioName(), data2.getPortfolioName());
        assertNotEquals(data1.getUsername(), data2.getUsername());
        assertNotEquals(data1.getTotalValue(), data2.getTotalValue(), 0.01);
    }
}
