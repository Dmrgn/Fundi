package interfaceadapter.watchlist;

import usecase.watchlist.TimeSeriesInputBoundary;
import usecase.watchlist.TimeSeriesRequest;

import javax.swing.*;

public class TimeSeriesController {
    private final TimeSeriesInputBoundary interactor;

    public TimeSeriesController(TimeSeriesInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void fetch(String symbol, String range) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                interactor.execute(new TimeSeriesRequest(symbol, range));
                return null;
            }
        };
        worker.execute();
    }
}
