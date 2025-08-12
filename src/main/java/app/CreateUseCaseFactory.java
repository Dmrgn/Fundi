package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.create.CreateController;
import interfaceadapter.create.CreatePresenter;
import interfaceadapter.create.CreateViewModel;
import interfaceadapter.portfolio_hub.PortfolioHubViewModel;
import usecase.create.CreateDataAccessInterface;
import usecase.create.CreateInputBoundary;
import usecase.create.CreateInteractor;
import usecase.create.CreateOutputBoundary;

/**
 * Factory for the Create Use Case.
 */
public final class CreateUseCaseFactory {
    private CreateUseCaseFactory() {

    }

    /**
     * Create the Create Controller.
     * @param viewManagerModel The View Manager Model
     * @param portfolioHubViewModel The Portfolio Hub View Model
     * @param createViewModel The Create View Model
     * @param dataAccessObject The DAO
     * @return The Create Controller
     */
    public static CreateController create(
            ViewManagerModel viewManagerModel,
            PortfolioHubViewModel portfolioHubViewModel,
            CreateViewModel createViewModel,
            CreateDataAccessInterface dataAccessObject
    ) {
        CreateOutputBoundary createPresenter = new CreatePresenter(
                viewManagerModel,
                portfolioHubViewModel,
                createViewModel
        );
        CreateInputBoundary createInteractor = new CreateInteractor(
                dataAccessObject,
                createPresenter
        );
        return new CreateController(createInteractor);
    }
}
