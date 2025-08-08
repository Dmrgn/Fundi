package use_case.watchlist;

import entity.TimeSeriesPoint;

import java.util.List;

public class TimeSeriesInteractor implements TimeSeriesInputBoundary {
    private final TimeSeriesDataAccess dataAccess;
    private final TimeSeriesOutputBoundary presenter;

    public TimeSeriesInteractor(TimeSeriesDataAccess dataAccess, TimeSeriesOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(TimeSeriesRequest request) {
        try {
            List<TimeSeriesPoint> points = dataAccess.getTimeSeries(request.symbol, request.range);
            presenter.present(new TimeSeriesResponse(request.symbol, points, null, request.range));
        } catch (Exception ex) {
            presenter.present(
                    new TimeSeriesResponse(request.symbol, java.util.List.of(), ex.getMessage(), request.range));
        }
    }
}
