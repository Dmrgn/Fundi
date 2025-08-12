package interfaceadapter.history;

import interfaceadapter.ViewModel;

/**
 * The view model for the History View.
 */
public class HistoryViewModel extends ViewModel<HistoryState> {

    public HistoryViewModel() {
        super("history");
        setState(new HistoryState());
    }
}
