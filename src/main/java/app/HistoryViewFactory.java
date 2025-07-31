package app;

import interface_adapter.history.HistoryController;
import interface_adapter.history.HistoryViewModel;
import interface_adapter.navigation.NavigationController;
import view.HistoryView;

public class HistoryViewFactory {
    private HistoryViewFactory() {}

    public static HistoryView create(
        HistoryViewModel historyViewModel, HistoryController historyController, NavigationController navigationController
    ) {
        return new HistoryView(historyViewModel, historyController, navigationController);
    }
}
