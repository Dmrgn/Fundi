package app;

import dataaccess.DBUserDataAccessObject;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.watchlist.WatchlistController;
import interfaceadapter.watchlist.WatchlistViewModel;
import view.WatchlistView;

/**
 * Factory for the Watchlist View.
 */
public class WatchlistViewFactory {
    private WatchlistViewFactory() {

    }

    public static WatchlistView create(MainViewModel mainViewModel, DBUserDataAccessObject userDataAccessObject) {
        WatchlistViewModel watchlistViewModel = new WatchlistViewModel();
        WatchlistController watchlistController = WatchlistUseCaseFactory.create(watchlistViewModel,
                userDataAccessObject);
        return new WatchlistView(mainViewModel, watchlistViewModel, watchlistController);
    }
}
