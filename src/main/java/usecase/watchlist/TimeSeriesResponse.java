package usecase.watchlist;

import entity.TimeSeriesPoint;
import java.util.List;

public class TimeSeriesResponse {
    public final String symbol;
    public final List<TimeSeriesPoint> points;
    public final String error; // null if ok
    public final String range; // "1W" | "1M" | "3M"

    public TimeSeriesResponse(String symbol, List<TimeSeriesPoint> points, String error, String range) {
        this.symbol = symbol;
        this.points = points;
        this.error = error;
        this.range = range;
    }
}
