package usecase.leaderboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardInputDataTest {

    @Test
    void constructor_ShouldCreateValidInstance() {
        // Act
        LeaderboardInputData inputData = new LeaderboardInputData();

        // Assert
        assertNotNull(inputData);
    }

    @Test
    void multipleInstances_ShouldBeIndependent() {
        // Act
        LeaderboardInputData inputData1 = new LeaderboardInputData();
        LeaderboardInputData inputData2 = new LeaderboardInputData();

        // Assert
        assertNotNull(inputData1);
        assertNotNull(inputData2);
        assertNotSame(inputData1, inputData2);
    }
}
