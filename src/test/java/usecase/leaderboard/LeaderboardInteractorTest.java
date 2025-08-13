package usecase.leaderboard;

import entity.LeaderboardEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaderboardInteractorTest {

    @Mock
    private LeaderboardDataAccessInterface dataAccess;

    @Mock
    private LeaderboardOutputBoundary presenter;

    private LeaderboardInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new LeaderboardInteractor(dataAccess, presenter);
    }

    @Test
    void execute_WithValidData_ShouldPresentSuccessfulLeaderboard() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();

        List<LeaderboardDataAccessInterface.PortfolioLeaderboardData> mockPortfolioData = Arrays.asList(
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Tech Portfolio", "alice", 15000.0),
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Growth Fund", "bob", 12500.0),
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Blue Chips", "charlie", 10000.0));

        when(dataAccess.getPortfolioLeaderboardData()).thenReturn(mockPortfolioData);

        // Act
        interactor.execute(inputData);

        // Assert
        ArgumentCaptor<LeaderboardOutputData> outputCaptor = ArgumentCaptor.forClass(LeaderboardOutputData.class);
        verify(presenter).prepareSuccessView(outputCaptor.capture());

        LeaderboardOutputData outputData = outputCaptor.getValue();
        assertTrue(outputData.isSuccess());
        assertEquals(3, outputData.getLeaderboardEntries().size());

        // Verify correct ranking assignment
        List<LeaderboardEntry> entries = outputData.getLeaderboardEntries();

        // First place
        assertEquals(1, entries.get(0).getRank());
        assertEquals("Tech Portfolio", entries.get(0).getPortfolioName());
        assertEquals("alice", entries.get(0).getUsername());
        assertEquals(15000.0, entries.get(0).getTotalValue(), 0.01);

        // Second place
        assertEquals(2, entries.get(1).getRank());
        assertEquals("Growth Fund", entries.get(1).getPortfolioName());
        assertEquals("bob", entries.get(1).getUsername());
        assertEquals(12500.0, entries.get(1).getTotalValue(), 0.01);

        // Third place
        assertEquals(3, entries.get(2).getRank());
        assertEquals("Blue Chips", entries.get(2).getPortfolioName());
        assertEquals("charlie", entries.get(2).getUsername());
        assertEquals(10000.0, entries.get(2).getTotalValue(), 0.01);
    }

    @Test
    void execute_WithEmptyData_ShouldPresentSuccessfulEmptyLeaderboard() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();

        List<LeaderboardDataAccessInterface.PortfolioLeaderboardData> emptyData = Arrays.asList();
        when(dataAccess.getPortfolioLeaderboardData()).thenReturn(emptyData);

        // Act
        interactor.execute(inputData);

        // Assert
        ArgumentCaptor<LeaderboardOutputData> outputCaptor = ArgumentCaptor.forClass(LeaderboardOutputData.class);
        verify(presenter).prepareSuccessView(outputCaptor.capture());

        LeaderboardOutputData outputData = outputCaptor.getValue();
        assertTrue(outputData.isSuccess());
        assertEquals(0, outputData.getLeaderboardEntries().size());
    }

    @Test
    void execute_WithSingleEntry_ShouldAssignRankOne() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();

        List<LeaderboardDataAccessInterface.PortfolioLeaderboardData> singleData = Arrays.asList(
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Solo Portfolio", "user1", 5000.0));

        when(dataAccess.getPortfolioLeaderboardData()).thenReturn(singleData);

        // Act
        interactor.execute(inputData);

        // Assert
        ArgumentCaptor<LeaderboardOutputData> outputCaptor = ArgumentCaptor.forClass(LeaderboardOutputData.class);
        verify(presenter).prepareSuccessView(outputCaptor.capture());

        LeaderboardOutputData outputData = outputCaptor.getValue();
        assertTrue(outputData.isSuccess());
        assertEquals(1, outputData.getLeaderboardEntries().size());

        LeaderboardEntry entry = outputData.getLeaderboardEntries().get(0);
        assertEquals(1, entry.getRank());
        assertEquals("Solo Portfolio", entry.getPortfolioName());
        assertEquals("user1", entry.getUsername());
        assertEquals(5000.0, entry.getTotalValue(), 0.01);
    }

    @Test
    void execute_WithZeroValues_ShouldHandleCorrectly() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();

        List<LeaderboardDataAccessInterface.PortfolioLeaderboardData> zeroValueData = Arrays.asList(
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Empty Portfolio", "user1", 0.0),
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Also Empty", "user2", 0.0));

        when(dataAccess.getPortfolioLeaderboardData()).thenReturn(zeroValueData);

        // Act
        interactor.execute(inputData);

        // Assert
        ArgumentCaptor<LeaderboardOutputData> outputCaptor = ArgumentCaptor.forClass(LeaderboardOutputData.class);
        verify(presenter).prepareSuccessView(outputCaptor.capture());

        LeaderboardOutputData outputData = outputCaptor.getValue();
        assertTrue(outputData.isSuccess());
        assertEquals(2, outputData.getLeaderboardEntries().size());

        // Both should have correct ranks assigned
        assertEquals(1, outputData.getLeaderboardEntries().get(0).getRank());
        assertEquals(2, outputData.getLeaderboardEntries().get(1).getRank());
        assertEquals(0.0, outputData.getLeaderboardEntries().get(0).getTotalValue(), 0.01);
        assertEquals(0.0, outputData.getLeaderboardEntries().get(1).getTotalValue(), 0.01);
    }

    @Test
    void execute_WithLargeNumbers_ShouldHandleCorrectly() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();

        List<LeaderboardDataAccessInterface.PortfolioLeaderboardData> largeNumberData = Arrays.asList(
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Whale Portfolio", "bigplayer",
                        1_000_000.50),
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Medium Portfolio", "regularuser",
                        50_000.75));

        when(dataAccess.getPortfolioLeaderboardData()).thenReturn(largeNumberData);

        // Act
        interactor.execute(inputData);

        // Assert
        ArgumentCaptor<LeaderboardOutputData> outputCaptor = ArgumentCaptor.forClass(LeaderboardOutputData.class);
        verify(presenter).prepareSuccessView(outputCaptor.capture());

        LeaderboardOutputData outputData = outputCaptor.getValue();
        assertTrue(outputData.isSuccess());

        LeaderboardEntry firstEntry = outputData.getLeaderboardEntries().get(0);
        assertEquals(1_000_000.50, firstEntry.getTotalValue(), 0.01);
        assertEquals("Whale Portfolio", firstEntry.getPortfolioName());

        LeaderboardEntry secondEntry = outputData.getLeaderboardEntries().get(1);
        assertEquals(50_000.75, secondEntry.getTotalValue(), 0.01);
        assertEquals("Medium Portfolio", secondEntry.getPortfolioName());
    }

    @Test
    void execute_WithDataAccessException_ShouldPresentFailureView() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();
        String errorMessage = "Database connection failed";

        when(dataAccess.getPortfolioLeaderboardData()).thenThrow(new RuntimeException(errorMessage));

        // Act
        interactor.execute(inputData);

        // Assert
        verify(presenter).prepareFailView("Failed to generate leaderboard: " + errorMessage);
        verify(presenter, never()).prepareSuccessView(any(LeaderboardOutputData.class));
    }

    @Test
    void execute_WithNullPointerException_ShouldPresentFailureView() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();

        when(dataAccess.getPortfolioLeaderboardData()).thenThrow(new NullPointerException("Null data"));

        // Act
        interactor.execute(inputData);

        // Assert
        verify(presenter).prepareFailView("Failed to generate leaderboard: Null data");
        verify(presenter, never()).prepareSuccessView(any(LeaderboardOutputData.class));
    }

    @Test
    void execute_WithSQLException_ShouldPresentFailureView() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();
        String sqlError = "SQL syntax error in query";

        when(dataAccess.getPortfolioLeaderboardData()).thenThrow(new RuntimeException(sqlError));

        // Act
        interactor.execute(inputData);

        // Assert
        verify(presenter).prepareFailView("Failed to generate leaderboard: " + sqlError);
    }

    @Test
    void execute_VerifyDataAccessMethodCalled() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();

        when(dataAccess.getPortfolioLeaderboardData()).thenReturn(Arrays.asList());

        // Act
        interactor.execute(inputData);

        // Assert
        verify(dataAccess, times(1)).getPortfolioLeaderboardData();
        verify(presenter, times(1)).prepareSuccessView(any(LeaderboardOutputData.class));
    }

    @Test
    void execute_WithManyEntries_ShouldAssignConsecutiveRanks() throws Exception {
        // Arrange
        LeaderboardInputData inputData = new LeaderboardInputData();

        List<LeaderboardDataAccessInterface.PortfolioLeaderboardData> manyEntries = Arrays.asList(
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Portfolio A", "user1", 1000.0),
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Portfolio B", "user2", 900.0),
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Portfolio C", "user3", 800.0),
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Portfolio D", "user4", 700.0),
                new LeaderboardDataAccessInterface.PortfolioLeaderboardData("Portfolio E", "user5", 600.0));

        when(dataAccess.getPortfolioLeaderboardData()).thenReturn(manyEntries);

        // Act
        interactor.execute(inputData);

        // Assert
        ArgumentCaptor<LeaderboardOutputData> outputCaptor = ArgumentCaptor.forClass(LeaderboardOutputData.class);
        verify(presenter).prepareSuccessView(outputCaptor.capture());

        LeaderboardOutputData outputData = outputCaptor.getValue();
        List<LeaderboardEntry> entries = outputData.getLeaderboardEntries();

        assertEquals(5, entries.size());

        // Verify consecutive ranks
        for (int i = 0; i < entries.size(); i++) {
            assertEquals(i + 1, entries.get(i).getRank(), "Rank should be " + (i + 1) + " for index " + i);
        }
    }
}
