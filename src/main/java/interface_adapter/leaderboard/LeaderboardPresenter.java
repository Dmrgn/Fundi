package interface_adapter.leaderboard;

import use_case.leaderboard.LeaderboardOutputBoundary;
import use_case.leaderboard.LeaderboardOutputData;

/**
 * Presenter for the leaderboard use case.
 */
public class LeaderboardPresenter implements LeaderboardOutputBoundary {
    private final LeaderboardViewModel viewModel;

    public LeaderboardPresenter(LeaderboardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(LeaderboardOutputData outputData) {
        LeaderboardState state = viewModel.getState();
        state.setLeaderboardEntries(outputData.getLeaderboardEntries());
        state.setError(null);
        state.setLoading(false);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        LeaderboardState state = viewModel.getState();
        state.setError(error);
        state.setLoading(false);
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
