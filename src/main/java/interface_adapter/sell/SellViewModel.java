package interface_adapter.sell;

import interface_adapter.ViewModel;

public class SellViewModel extends ViewModel<SellState> {
    public SellViewModel() {
        super("sell");
        setState(new SellState());
    }
}
