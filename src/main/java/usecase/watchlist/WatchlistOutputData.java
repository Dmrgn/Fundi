package usecase.watchlist;

import java.util.List;

public class WatchlistOutputData {
    private final List<String> tickers;
    private final String username;

    public WatchlistOutputData(String username, List<String> tickers) {
        this.username = username;
        this.tickers = tickers;
    }

    public List<String> getTickers() {
        return tickers;
    }

    public String getUsername() {
        return username;
    }
}
