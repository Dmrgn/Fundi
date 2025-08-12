package app;

import interfaceadapter.history.HistoryViewModel;
import interfaceadapter.navigation.NavigationController;
import view.HistoryView;

/**
 * Factory for the History View.
 */
public final class HistoryViewFactory {
    private HistoryViewFactory() {

    }

    /**
     * Create the History View.
     * @param historyViewModel The History View Model
     * @param navigationController The Navigation Controller
     * @return The History View
     */
    public static HistoryView create(
        HistoryViewModel historyViewModel, NavigationController navigationController
    ) {
        return new HistoryView(historyViewModel, navigationController);
    }
}
