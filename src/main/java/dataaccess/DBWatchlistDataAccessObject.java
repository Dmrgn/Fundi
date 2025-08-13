package dataaccess;

import usecase.watchlist.WatchlistDataAccessInterface;
import java.util.List;

public class DBWatchlistDataAccessObject implements WatchlistDataAccessInterface {
    private final DBUserDataAccessObject userDao;

    public DBWatchlistDataAccessObject(DBUserDataAccessObject userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addToWatchlist(String username, String ticker) throws Exception {
        userDao.addToWatchlist(username, ticker);
    }

    @Override
    public void removeFromWatchlist(String username, String ticker) throws Exception {
        userDao.removeFromWatchlist(username, ticker);
    }

    @Override
    public List<String> getWatchlist(String username) throws Exception {
        return userDao.getWatchlist(username);
    }
}
