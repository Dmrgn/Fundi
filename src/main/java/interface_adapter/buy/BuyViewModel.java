package interface_adapter.buy;

import interface_adapter.ViewModel;

/**
 * The view model for the Buy View
 */
public class BuyViewModel extends ViewModel<BuyState> {

    public BuyViewModel() {
        super("buy");
        setState(new BuyState());
    }
}
