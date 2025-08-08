package app;

import interface_adapter.navigation.NavigationController;
import interface_adapter.shortsell.ShortController;
import interface_adapter.shortsell.ShortViewModel;
import view.ShortView;

public final class ShortViewFactory {
    private ShortViewFactory() {}

    public static ShortView create(ShortViewModel viewModel, ShortController controller, NavigationController navigationController) {
        return new ShortView(viewModel, controller, navigationController);
    }
}
