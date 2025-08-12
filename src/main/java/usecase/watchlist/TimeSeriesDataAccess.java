package usecase.watchlist;

import entity.TimeSeriesPoint;
import java.util.List;

public interface TimeSeriesDataAccess {
    List<TimeSeriesPoint> getTimeSeries(String symbol, String range) throws Exception;
}
