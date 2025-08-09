package interface_adapter.watchlist;

import use_case.watchlist.TimeSeriesOutputBoundary;
import use_case.watchlist.TimeSeriesResponse;

import javax.swing.SwingUtilities;

public class TimeSeriesPresenter implements TimeSeriesOutputBoundary {
    private final TimeSeriesViewModel vm;

    public TimeSeriesPresenter(TimeSeriesViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void present(TimeSeriesResponse response) {
        SwingUtilities.invokeLater(() -> vm.setData(response.symbol, response.points, response.error, response.range));
    }
}
