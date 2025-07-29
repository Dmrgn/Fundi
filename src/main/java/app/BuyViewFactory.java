package app;

import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyViewModel;
import view.BuyView;

public class BuyViewFactory {
    private BuyViewFactory() {

    }

    public static BuyView create(BuyViewModel viewModel, BuyController controller) {
        return new BuyView(viewModel, controller);
    }
}
