package interface_adapter.leaderboard;

import entity.LeaderboardEntry;
import java.util.List;

/**
 * State for the leaderboard view model.
 */
public class LeaderboardState {
    private List<LeaderboardEntry> leaderboardEntries;
    private String error;
    private boolean loading;

    public LeaderboardState() {
        this.loading = false;
    }

    public List<LeaderboardEntry> getLeaderboardEntries() {
        return leaderboardEntries;
    }

    public void setLeaderboardEntries(List<LeaderboardEntry> leaderboardEntries) {
        this.leaderboardEntries = leaderboardEntries;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
