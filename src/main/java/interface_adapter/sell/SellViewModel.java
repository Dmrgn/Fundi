package interface_adapter.sell;

import interface_adapter.ViewModel;

/**
 * The View Model for the Sell View
 */
public class SellViewModel extends ViewModel<SellState> {
    public SellViewModel() {
        super("sell");
        setState(new SellState());
    }
}
