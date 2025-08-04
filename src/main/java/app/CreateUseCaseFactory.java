package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateController;
import interface_adapter.create.CreatePresenter;
import interface_adapter.create.CreateViewModel;
import interface_adapter.portfolio_hub.PortfolioHubViewModel;
import use_case.create.CreateDataAccessInterface;
import use_case.create.CreateInputBoundary;
import use_case.create.CreateInteractor;
import use_case.create.CreateOutputBoundary;

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
