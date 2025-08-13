package usecase.watchlist;

public interface WatchlistInputBoundary {
    void addTicker(WatchlistInputData inputData);

    void removeTicker(WatchlistInputData inputData);

    void fetchWatchlist(String username);
}
