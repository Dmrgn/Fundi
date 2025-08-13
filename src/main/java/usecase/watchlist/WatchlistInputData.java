package usecase.watchlist;

public class WatchlistInputData {
    private final String username;
    private final String ticker;

    public WatchlistInputData(String username, String ticker) {
        this.username = username;
        this.ticker = ticker;
    }

    public String getUsername() {
        return username;
    }

    public String getTicker() {
        return ticker;
    }
}
