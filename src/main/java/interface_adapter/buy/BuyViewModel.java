package interface_adapter.buy;

import interface_adapter.ViewModel;

public class BuyViewModel extends ViewModel<BuyState> {

    public BuyViewModel() {
        super("buy");
        setState(new BuyState());
    }
}
