package app;

import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.navigation.NavigationController;
import view.BuyView;

public class BuyViewFactory {
    private BuyViewFactory() {

    }

    public static BuyView create(BuyViewModel viewModel, BuyController controller, NavigationController navigationController) {
        return new BuyView(viewModel, controller, navigationController);
    }
}
