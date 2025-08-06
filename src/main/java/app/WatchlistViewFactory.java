package app;

import data_access.DBUserDataAccessObject;
import interface_adapter.main.MainViewModel;
import view.WatchlistView;

/**
 * Factory for the Watchlist View
 */
public class WatchlistViewFactory {
    private WatchlistViewFactory() {

    }

    public static WatchlistView create(MainViewModel mainViewModel, DBUserDataAccessObject userDataAccessObject) {
        return new WatchlistView(mainViewModel, userDataAccessObject);
    }
}
