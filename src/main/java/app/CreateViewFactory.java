package app;

import interface_adapter.create.CreateController;
import interface_adapter.create.CreateViewModel;
import view.CreateView;

public class CreateViewFactory {
    private CreateViewFactory() {

    }

    public static CreateView create(
            CreateViewModel createViewModel,
            CreateController createController
    ) {
        return new CreateView(createViewModel, createController);
    }
}
