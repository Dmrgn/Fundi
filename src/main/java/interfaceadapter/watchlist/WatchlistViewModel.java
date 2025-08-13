package interfaceadapter.watchlist;

import interfaceadapter.ViewModel;
import java.util.List;

public class WatchlistViewModel extends ViewModel<WatchlistState> {
    public static final String WATCHLIST_UPDATED_PROPERTY = "watchlistUpdated";
    public static final String ERROR_PROPERTY = "error";
    public static final String SUCCESS_PROPERTY = "success";

    public WatchlistViewModel() {
        super("watchlist");
        setState(new WatchlistState());
    }

    public void fireWatchlistUpdated() {
        firePropertyChanged(WATCHLIST_UPDATED_PROPERTY);
    }

    public void fireError() {
        firePropertyChanged(ERROR_PROPERTY);
    }

    public void fireSuccess() {
        firePropertyChanged(SUCCESS_PROPERTY);
    }
}
