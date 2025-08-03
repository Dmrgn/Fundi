package app;

import interface_adapter.create.CreateController;
import interface_adapter.create.CreateViewModel;
import interface_adapter.ViewManagerModel;
import view.CreateView;

/**
 * Factory for the Create View
 */
public class CreateViewFactory {
    private CreateViewFactory() {

    }

    public static CreateView create(
            CreateViewModel createViewModel,
            CreateController createController,
            ViewManagerModel viewManagerModel) {
        return new CreateView(createViewModel, createController, viewManagerModel);
    }
}
