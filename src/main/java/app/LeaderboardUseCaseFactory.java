package app;

import dataaccess.DBLeaderboardDataAccessObject;
import interfaceadapter.leaderboard.LeaderboardController;
import interfaceadapter.leaderboard.LeaderboardPresenter;
import interfaceadapter.leaderboard.LeaderboardViewModel;
import usecase.leaderboard.LeaderboardInputBoundary;
import usecase.leaderboard.LeaderboardInteractor;
import usecase.leaderboard.LeaderboardOutputBoundary;

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
