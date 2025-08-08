package use_case.watchlist;

public class TimeSeriesRequest {
    public final String symbol;
    public final String range; // "1W", "1M", "3M"

    public TimeSeriesRequest(String symbol, String range) {
        this.symbol = symbol;
        this.range = range;
    }
}
