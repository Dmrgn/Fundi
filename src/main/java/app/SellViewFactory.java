package app;

import interface_adapter.sell.SellController;
import interface_adapter.sell.SellViewModel;
import view.SellView;

public class SellViewFactory {
    private SellViewFactory() {

    }

    public static SellView create(SellViewModel viewModel, SellController controller) {
        return new SellView(viewModel, controller);
    }
}
