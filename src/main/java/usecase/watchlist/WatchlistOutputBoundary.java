package usecase.watchlist;

public interface WatchlistOutputBoundary {
    void presentWatchlist(WatchlistOutputData outputData);

    void presentSuccess(String message);

    void presentError(String error);
}
