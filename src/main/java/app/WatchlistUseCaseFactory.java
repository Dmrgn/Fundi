package app;

import dataaccess.DBUserDataAccessObject;
import dataaccess.DBWatchlistDataAccessObject;
import interfaceadapter.watchlist.WatchlistController;
import interfaceadapter.watchlist.WatchlistPresenter;
import interfaceadapter.watchlist.WatchlistViewModel;
import usecase.watchlist.WatchlistInteractor;

public final class WatchlistUseCaseFactory {
    private WatchlistUseCaseFactory() {
    }

    public static WatchlistController create(
            WatchlistViewModel viewModel,
            DBUserDataAccessObject userDao) {
        DBWatchlistDataAccessObject watchlistDao = new DBWatchlistDataAccessObject(userDao);
        WatchlistPresenter presenter = new WatchlistPresenter(viewModel);
        WatchlistInteractor interactor = new WatchlistInteractor(watchlistDao, presenter);
        return new WatchlistController(interactor);
    }
}
