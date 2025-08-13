package interfaceadapter.watchlist;

import usecase.watchlist.WatchlistInputBoundary;
import usecase.watchlist.WatchlistInputData;

public class WatchlistController {
    private final WatchlistInputBoundary interactor;

    public WatchlistController(WatchlistInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void addTicker(String username, String ticker) {
        interactor.addTicker(new WatchlistInputData(username, ticker));
    }

    public void removeTicker(String username, String ticker) {
        interactor.removeTicker(new WatchlistInputData(username, ticker));
    }

    public void refreshWatchlist(String username) {
        interactor.fetchWatchlist(username);
    }
}
