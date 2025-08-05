package app;

import data_access.DBLeaderboardDataAccessObject;
import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardPresenter;
import interface_adapter.leaderboard.LeaderboardViewModel;
import use_case.leaderboard.LeaderboardInputBoundary;
import use_case.leaderboard.LeaderboardInteractor;
import use_case.leaderboard.LeaderboardOutputBoundary;

import java.sql.SQLException;

/**
 * Factory for creating Leaderboard use case components.
 */
public class LeaderboardUseCaseFactory {

    /**
     * Prevents instantiation.
     */
    private LeaderboardUseCaseFactory() {
    }

    /**
     * Creates a LeaderboardController with all necessary dependencies.
     * 
     * @param leaderboardViewModel the leaderboard view model
     * @return the leaderboard controller
     */
    public static LeaderboardController createLeaderboardController(LeaderboardViewModel leaderboardViewModel) {
        try {
            final LeaderboardOutputBoundary leaderboardOutputBoundary = new LeaderboardPresenter(leaderboardViewModel);
            final DBLeaderboardDataAccessObject leaderboardDataAccessObject = new DBLeaderboardDataAccessObject();
            final LeaderboardInputBoundary leaderboardInteractor = new LeaderboardInteractor(
                    leaderboardDataAccessObject, leaderboardOutputBoundary);

            return new LeaderboardController(leaderboardInteractor);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create leaderboard controller: " + e.getMessage(), e);
        }
    }
}
