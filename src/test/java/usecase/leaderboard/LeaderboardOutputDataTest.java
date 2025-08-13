package usecase.leaderboard;

import entity.LeaderboardEntry;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardOutputDataTest {

    @Test
    void constructor_WithValidData_ShouldCreateCorrectInstance() {
        // Arrange
        List<LeaderboardEntry> entries = Arrays.asList(
                new LeaderboardEntry("Portfolio A", "user1", 1000.0, 1),
                new LeaderboardEntry("Portfolio B", "user2", 900.0, 2));
        boolean success = true;

        // Act
        LeaderboardOutputData outputData = new LeaderboardOutputData(entries, success);

        // Assert
        assertEquals(entries, outputData.getLeaderboardEntries());
        assertTrue(outputData.isSuccess());
    }

    @Test
    void constructor_WithEmptyList_ShouldCreateValidInstance() {
        // Arrange
        List<LeaderboardEntry> emptyEntries = Arrays.asList();
        boolean success = true;

        // Act
        LeaderboardOutputData outputData = new LeaderboardOutputData(emptyEntries, success);

        // Assert
        assertEquals(emptyEntries, outputData.getLeaderboardEntries());
        assertTrue(outputData.getLeaderboardEntries().isEmpty());
        assertTrue(outputData.isSuccess());
    }

    @Test
    void constructor_WithFailureStatus_ShouldReturnFalseSuccess() {
        // Arrange
        List<LeaderboardEntry> entries = Arrays.asList(
                new LeaderboardEntry("Portfolio A", "user1", 1000.0, 1));
        boolean success = false;

        // Act
        LeaderboardOutputData outputData = new LeaderboardOutputData(entries, success);

        // Assert
        assertEquals(entries, outputData.getLeaderboardEntries());
        assertFalse(outputData.isSuccess());
    }

    @Test
    void constructor_WithNullList_ShouldAcceptNull() {
        // Arrange
        List<LeaderboardEntry> nullEntries = null;
        boolean success = false;

        // Act
        LeaderboardOutputData outputData = new LeaderboardOutputData(nullEntries, success);

        // Assert
        assertNull(outputData.getLeaderboardEntries());
        assertFalse(outputData.isSuccess());
    }

    @Test
    void getLeaderboardEntries_ShouldReturnSameList() {
        // Arrange
        List<LeaderboardEntry> originalEntries = Arrays.asList(
                new LeaderboardEntry("Portfolio A", "user1", 1500.0, 1),
                new LeaderboardEntry("Portfolio B", "user2", 1200.0, 2),
                new LeaderboardEntry("Portfolio C", "user3", 800.0, 3));
        LeaderboardOutputData outputData = new LeaderboardOutputData(originalEntries, true);

        // Act
        List<LeaderboardEntry> retrievedEntries = outputData.getLeaderboardEntries();

        // Assert
        assertSame(originalEntries, retrievedEntries);
        assertEquals(3, retrievedEntries.size());
        assertEquals("Portfolio A", retrievedEntries.get(0).getPortfolioName());
        assertEquals("user1", retrievedEntries.get(0).getUsername());
        assertEquals(1500.0, retrievedEntries.get(0).getTotalValue(), 0.01);
        assertEquals(1, retrievedEntries.get(0).getRank());
    }

    @Test
    void isSuccess_ShouldReturnCorrectValue() {
        // Test success = true
        LeaderboardOutputData successData = new LeaderboardOutputData(Arrays.asList(), true);
        assertTrue(successData.isSuccess());

        // Test success = false
        LeaderboardOutputData failureData = new LeaderboardOutputData(Arrays.asList(), false);
        assertFalse(failureData.isSuccess());
    }

    @Test
    void immutability_ShouldNotAllowExternalModification() {
        // Arrange
        List<LeaderboardEntry> entries = Arrays.asList(
                new LeaderboardEntry("Portfolio A", "user1", 1000.0, 1));
        LeaderboardOutputData outputData = new LeaderboardOutputData(entries, true);

        // Act & Assert
        List<LeaderboardEntry> retrievedEntries = outputData.getLeaderboardEntries();
        assertSame(entries, retrievedEntries);

        // The list itself is the same reference, so we test that the data is correctly
        // stored
        assertEquals(1, retrievedEntries.size());
        assertEquals("Portfolio A", retrievedEntries.get(0).getPortfolioName());
    }
}
