package use_case.leaderboard;

import entity.LeaderboardEntry;
import java.util.*;

/**
 * Interactor for the leaderboard use case.
 */
public class LeaderboardInteractor implements LeaderboardInputBoundary {
    private final LeaderboardDataAccessInterface dataAccess;
    private final LeaderboardOutputBoundary presenter;

    public LeaderboardInteractor(LeaderboardDataAccessInterface dataAccess,
            LeaderboardOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(LeaderboardInputData leaderboardInputData) {
        try {
            List<LeaderboardDataAccessInterface.PortfolioLeaderboardData> portfolioData = dataAccess
                    .getPortfolioLeaderboardData();
            List<LeaderboardEntry> entries = new ArrayList<>();

            // Assign ranks (data is already sorted by database)
            int rank = 1;
            for (LeaderboardDataAccessInterface.PortfolioLeaderboardData data : portfolioData) {
                entries.add(new LeaderboardEntry(
                        data.getPortfolioName(),
                        data.getUsername(),
                        data.getTotalValue(),
                        rank++));
            }

            LeaderboardOutputData outputData = new LeaderboardOutputData(entries, true);
            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepareFailView("Failed to generate leaderboard: " + e.getMessage());
        }
    }
}
