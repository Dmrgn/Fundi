package app;

import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardViewModel;
import view.LeaderboardView;

/**
 * Factory for creating the Leaderboard View.
 */
public class LeaderboardViewFactory {

    /**
     * Prevents instantiation.
     */
    private LeaderboardViewFactory() {
    }

    /**
     * Creates a LeaderboardView with all necessary dependencies.
     * 
     * @param leaderboardViewModel  the leaderboard view model
     * @param leaderboardController the leaderboard controller
     * @param navigationController  the navigation controller
     * @return the leaderboard view
     */
    public static LeaderboardView create(LeaderboardViewModel leaderboardViewModel,
            LeaderboardController leaderboardController) {
        return new LeaderboardView(leaderboardViewModel, leaderboardController);
    }
}
