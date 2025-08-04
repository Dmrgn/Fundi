package interface_adapter.history;

import interface_adapter.ViewModel;

/**
 * The view model for the History View.
 */
public class HistoryViewModel extends ViewModel<HistoryState> {

    public HistoryViewModel() {
        super("history");
        setState(new HistoryState());
    }
}
