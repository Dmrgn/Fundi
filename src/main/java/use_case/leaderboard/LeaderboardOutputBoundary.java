package use_case.leaderboard;

/**
 * Output boundary for the leaderboard use case.
 */
public interface LeaderboardOutputBoundary {
    void prepareSuccessView(LeaderboardOutputData leaderboardOutputData);
    void prepareFailView(String error);
}
