package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.create.CreateController;
import interfaceadapter.create.CreateViewModel;
import view.CreateView;

/**
 * Factory for the Create View.
 */
public final class CreateViewFactory {
    private CreateViewFactory() {

    }

    /**
     * Create the Create View.
     * @param createViewModel The Create View Model
     * @param createController The Create Controller
     * @param viewManagerModel The View Manager Model
     * @return The Create View
     */
    public static CreateView create(
            CreateViewModel createViewModel,
            CreateController createController,
            ViewManagerModel viewManagerModel) {
        return new CreateView(createViewModel, createController, viewManagerModel);
    }
}
