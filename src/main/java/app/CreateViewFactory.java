package app;

import interface_adapter.create.CreateController;
import interface_adapter.create.CreateViewModel;
import interface_adapter.navigation.NavigationController;
import view.CreateView;

public class CreateViewFactory {
    private CreateViewFactory() {

    }

    public static CreateView create(
            CreateViewModel createViewModel,
            CreateController createController,
            NavigationController navigationController) {
        return new CreateView(createViewModel, createController, navigationController);
    }
}
