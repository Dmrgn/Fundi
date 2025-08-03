package app;

import interface_adapter.history.HistoryViewModel;
import interface_adapter.navigation.NavigationController;
import view.HistoryView;

/**
 * Factory for the History View
 */
public class HistoryViewFactory {
    private HistoryViewFactory() {}

    public static HistoryView create(
        HistoryViewModel historyViewModel, NavigationController navigationController
    ) {
        return new HistoryView(historyViewModel, navigationController);
    }
}
