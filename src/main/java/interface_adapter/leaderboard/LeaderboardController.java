package interface_adapter.leaderboard;

import use_case.leaderboard.LeaderboardInputBoundary;
import use_case.leaderboard.LeaderboardInputData;

/**
 * Controller for the leaderboard use case.
 */
public class LeaderboardController {
    private final LeaderboardInputBoundary leaderboardInteractor;

    public LeaderboardController(LeaderboardInputBoundary leaderboardInteractor) {
        this.leaderboardInteractor = leaderboardInteractor;
    }

    public void execute() {
        LeaderboardInputData inputData = new LeaderboardInputData();
        new javax.swing.SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                leaderboardInteractor.execute(inputData);
                return null;
            }
        }.execute();
    }
}
