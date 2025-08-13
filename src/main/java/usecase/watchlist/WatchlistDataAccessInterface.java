package usecase.watchlist;

public interface WatchlistDataAccessInterface {
    void addToWatchlist(String username, String ticker) throws Exception;

    void removeFromWatchlist(String username, String ticker) throws Exception;

    java.util.List<String> getWatchlist(String username) throws Exception;
}
