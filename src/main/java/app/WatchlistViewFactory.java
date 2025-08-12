package app;

import dataaccess.DBUserDataAccessObject;
import interfaceadapter.main.MainViewModel;
import view.WatchlistView;

/**
 * Factory for the Watchlist View.
 */
public class WatchlistViewFactory {
    private WatchlistViewFactory() {

    }

    public static WatchlistView create(MainViewModel mainViewModel, DBUserDataAccessObject userDataAccessObject) {
        return new WatchlistView(mainViewModel, userDataAccessObject);
    }
}
