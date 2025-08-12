package usecase.leaderboard;

import entity.LeaderboardEntry;
import java.util.List;

/**
 * Output data for the leaderboard use case.
 */
public class LeaderboardOutputData {
    private final List<LeaderboardEntry> leaderboardEntries;
    private final boolean success;

    public LeaderboardOutputData(List<LeaderboardEntry> leaderboardEntries, boolean success) {
        this.leaderboardEntries = leaderboardEntries;
        this.success = success;
    }

    public List<LeaderboardEntry> getLeaderboardEntries() {
        return leaderboardEntries;
    }

    public boolean isSuccess() {
        return success;
    }
}
