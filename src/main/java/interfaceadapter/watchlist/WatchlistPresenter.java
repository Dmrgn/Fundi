package interfaceadapter.watchlist;

import usecase.watchlist.WatchlistOutputBoundary;
import usecase.watchlist.WatchlistOutputData;

public class WatchlistPresenter implements WatchlistOutputBoundary {
    private final WatchlistViewModel viewModel;

    public WatchlistPresenter(WatchlistViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentWatchlist(WatchlistOutputData outputData) {
        WatchlistState state = viewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setTickers(outputData.getTickers());
        state.setErrorMessage("");
        state.setSuccessMessage("");
        viewModel.setState(state);
        viewModel.fireWatchlistUpdated();
    }

    @Override
    public void presentSuccess(String message) {
        WatchlistState state = viewModel.getState();
        state.setSuccessMessage(message);
        state.setErrorMessage("");
        viewModel.setState(state);
        viewModel.fireSuccess();
    }

    @Override
    public void presentError(String error) {
        WatchlistState state = viewModel.getState();
        state.setErrorMessage(error);
        state.setSuccessMessage("");
        viewModel.setState(state);
        viewModel.fireError();
    }
}
