package app;

import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.navigation.NavigationController;
import view.BuyView;

/**
 * Factory for the Buy View.
 */
public final class BuyViewFactory {
    private BuyViewFactory() {

    }

    /**
     * Create the Buy View.
     * @param viewModel The Buy View Model
     * @param controller The Buy Controller
     * @param navigationController The Navigation Controller
     * @return The Buy View
     */
    public static BuyView create(BuyViewModel viewModel, BuyController controller, NavigationController navigationController) {
        return new BuyView(viewModel, controller, navigationController);
    }
}
