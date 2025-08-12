package interfaceadapter.leaderboard;

import interfaceadapter.ViewModel;

/**
 * View model for the leaderboard view.
 */
public class LeaderboardViewModel extends ViewModel<LeaderboardState> {
    public static final String TITLE_LABEL = "Leaderboard";
    public static final String REFRESH_BUTTON_LABEL = "Refresh";

    public LeaderboardViewModel() {
        super("leaderboard");
        setState(new LeaderboardState());
    }
}
