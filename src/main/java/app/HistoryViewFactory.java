package app;

import interface_adapter.history.HistoryController;
import interface_adapter.history.HistoryViewModel;
import view.HistoryView;

public class HistoryViewFactory {
    private HistoryViewFactory() {}

    public static HistoryView create(HistoryViewModel historyViewModel, HistoryController historyController) {
        return new HistoryView(historyViewModel, historyController);
    }
}
