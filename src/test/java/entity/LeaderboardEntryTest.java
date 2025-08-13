package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardEntryTest {

    @Test
    void constructor_WithValidData_ShouldCreateCorrectInstance() {
        // Arrange
        String portfolioName = "Growth Portfolio";
        String username = "investor1";
        double totalValue = 12500.75;
        int rank = 1;

        // Act
        LeaderboardEntry entry = new LeaderboardEntry(portfolioName, username, totalValue, rank);

        // Assert
        assertEquals(portfolioName, entry.getPortfolioName());
        assertEquals(username, entry.getUsername());
        assertEquals(totalValue, entry.getTotalValue(), 0.01);
        assertEquals(rank, entry.getRank());
    }

    @Test
    void constructor_WithZeroValue_ShouldHandleCorrectly() {
        // Arrange
        String portfolioName = "Starting Portfolio";
        String username = "newbie";
        double totalValue = 0.0;
        int rank = 5;

        // Act
        LeaderboardEntry entry = new LeaderboardEntry(portfolioName, username, totalValue, rank);

        // Assert
        assertEquals(portfolioName, entry.getPortfolioName());
        assertEquals(username, entry.getUsername());
        assertEquals(0.0, entry.getTotalValue(), 0.01);
        assertEquals(rank, entry.getRank());
    }

    @Test
    void constructor_WithNegativeValue_ShouldAcceptNegativeValues() {
        // Arrange (losses case)
        String portfolioName = "Risky Portfolio";
        String username = "riskytaker";
        double totalValue = -1000.50;
        int rank = 10;

        // Act
        LeaderboardEntry entry = new LeaderboardEntry(portfolioName, username, totalValue, rank);

        // Assert
        assertEquals(portfolioName, entry.getPortfolioName());
        assertEquals(username, entry.getUsername());
        assertEquals(-1000.50, entry.getTotalValue(), 0.01);
        assertEquals(rank, entry.getRank());
    }

    @Test
    void constructor_WithLargeValue_ShouldHandleCorrectly() {
        // Arrange
        String portfolioName = "Millionaire Portfolio";
        String username = "wealthyuser";
        double totalValue = 1_500_000.99;
        int rank = 1;

        // Act
        LeaderboardEntry entry = new LeaderboardEntry(portfolioName, username, totalValue, rank);

        // Assert
        assertEquals(portfolioName, entry.getPortfolioName());
        assertEquals(username, entry.getUsername());
        assertEquals(1_500_000.99, entry.getTotalValue(), 0.01);
        assertEquals(rank, entry.getRank());
    }

    @Test
    void constructor_WithNullStrings_ShouldAcceptNullValues() {
        // Arrange
        String portfolioName = null;
        String username = null;
        double totalValue = 5000.0;
        int rank = 3;

        // Act
        LeaderboardEntry entry = new LeaderboardEntry(portfolioName, username, totalValue, rank);

        // Assert
        assertNull(entry.getPortfolioName());
        assertNull(entry.getUsername());
        assertEquals(5000.0, entry.getTotalValue(), 0.01);
        assertEquals(rank, entry.getRank());
    }

    @Test
    void constructor_WithEmptyStrings_ShouldHandleEmptyValues() {
        // Arrange
        String portfolioName = "";
        String username = "";
        double totalValue = 2500.25;
        int rank = 7;

        // Act
        LeaderboardEntry entry = new LeaderboardEntry(portfolioName, username, totalValue, rank);

        // Assert
        assertEquals("", entry.getPortfolioName());
        assertEquals("", entry.getUsername());
        assertEquals(2500.25, entry.getTotalValue(), 0.01);
        assertEquals(rank, entry.getRank());
    }

    @Test
    void constructor_WithSpecialCharacters_ShouldHandleCorrectly() {
        // Arrange
        String portfolioName = "Pörtfölio 2023 (special) & more!";
        String username = "üser-123@test.com";
        double totalValue = 4444.44;
        int rank = 2;

        // Act
        LeaderboardEntry entry = new LeaderboardEntry(portfolioName, username, totalValue, rank);

        // Assert
        assertEquals("Pörtfölio 2023 (special) & more!", entry.getPortfolioName());
        assertEquals("üser-123@test.com", entry.getUsername());
        assertEquals(4444.44, entry.getTotalValue(), 0.01);
        assertEquals(rank, entry.getRank());
    }

    @Test
    void constructor_WithHighRank_ShouldAcceptLargeRankNumbers() {
        // Arrange
        String portfolioName = "Bottom Portfolio";
        String username = "lastplace";
        double totalValue = 10.0;
        int rank = 1000;

        // Act
        LeaderboardEntry entry = new LeaderboardEntry(portfolioName, username, totalValue, rank);

        // Assert
        assertEquals(portfolioName, entry.getPortfolioName());
        assertEquals(username, entry.getUsername());
        assertEquals(10.0, entry.getTotalValue(), 0.01);
        assertEquals(1000, entry.getRank());
    }

    @Test
    void constructor_WithZeroOrNegativeRank_ShouldAcceptAnyRankValue() {
        // Arrange - Testing edge cases for rank
        LeaderboardEntry entryZeroRank = new LeaderboardEntry("Test", "user", 100.0, 0);
        LeaderboardEntry entryNegativeRank = new LeaderboardEntry("Test", "user", 100.0, -1);

        // Assert
        assertEquals(0, entryZeroRank.getRank());
        assertEquals(-1, entryNegativeRank.getRank());
    }

    @Test
    void getters_ShouldReturnSameValues() {
        // Arrange
        String originalPortfolioName = "Consistent Portfolio";
        String originalUsername = "consistentuser";
        double originalTotalValue = 8888.88;
        int originalRank = 4;

        LeaderboardEntry entry = new LeaderboardEntry(originalPortfolioName, originalUsername, originalTotalValue,
                originalRank);

        // Act & Assert
        assertEquals(originalPortfolioName, entry.getPortfolioName());
        assertEquals(originalUsername, entry.getUsername());
        assertEquals(originalTotalValue, entry.getTotalValue(), 0.01);
        assertEquals(originalRank, entry.getRank());

        // Multiple calls should return the same values
        assertEquals(originalPortfolioName, entry.getPortfolioName());
        assertEquals(originalUsername, entry.getUsername());
        assertEquals(originalTotalValue, entry.getTotalValue(), 0.01);
        assertEquals(originalRank, entry.getRank());
    }

    @Test
    void multipleInstances_ShouldBeIndependent() {
        // Arrange & Act
        LeaderboardEntry entry1 = new LeaderboardEntry("Portfolio 1", "user1", 1000.0, 1);
        LeaderboardEntry entry2 = new LeaderboardEntry("Portfolio 2", "user2", 2000.0, 2);

        // Assert
        assertNotSame(entry1, entry2);
        assertNotEquals(entry1.getPortfolioName(), entry2.getPortfolioName());
        assertNotEquals(entry1.getUsername(), entry2.getUsername());
        assertNotEquals(entry1.getTotalValue(), entry2.getTotalValue(), 0.01);
        assertNotEquals(entry1.getRank(), entry2.getRank());
    }

    @Test
    void immutability_ShouldNotAllowModificationAfterCreation() {
        // Arrange
        String portfolioName = "Immutable Portfolio";
        String username = "immutableuser";
        double totalValue = 9999.99;
        int rank = 1;

        // Act
        LeaderboardEntry entry = new LeaderboardEntry(portfolioName, username, totalValue, rank);

        // Assert - The entry should be immutable (final fields)
        // We verify this by checking that getters always return the same values
        assertEquals(portfolioName, entry.getPortfolioName());
        assertEquals(username, entry.getUsername());
        assertEquals(totalValue, entry.getTotalValue(), 0.01);
        assertEquals(rank, entry.getRank());

        // Note: Since all fields are final, there are no setters to test
        // The class is effectively immutable by design
    }
}
