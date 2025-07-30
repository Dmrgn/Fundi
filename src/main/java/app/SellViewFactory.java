package app;

import interface_adapter.sell.SellController;
import interface_adapter.sell.SellViewModel;
import view.SellView;
import interface_adapter.navigation.NavigationController;

public class SellViewFactory {
    private SellViewFactory() {

    }

    public static SellView create(SellViewModel viewModel, SellController controller, NavigationController navigationController) {
        return new SellView(viewModel, controller, navigationController);
    }
}
