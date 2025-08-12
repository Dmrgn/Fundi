package app;

import interfaceadapter.navigation.NavigationController;
import interfaceadapter.sell.SellController;
import interfaceadapter.sell.SellViewModel;
import view.SellView;

/**
 * Factory for the Sell View.
 */
public final class SellViewFactory {
    private SellViewFactory() {

    }

    /**
     * Create the Sell View.
     * @param viewModel The Sell View Model
     * @param controller The Sell Controller
     * @param navigationController The Navigation Controller
     * @return The Sell View
     */
    public static SellView create(SellViewModel viewModel, SellController controller, NavigationController navigationController) {
        return new SellView(viewModel, controller, navigationController);
    }
}
