package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.create.CreateViewModel;
import interfaceadapter.portfolio_hub.PortfolioHubController;
import interfaceadapter.portfolio_hub.PortfolioHubPresenter;
import interfaceadapter.portfolio_hub.PortfolioHubViewModel;
import usecase.portfolio_hub.PortfolioHubDataAccessInterface;
import usecase.portfolio_hub.PortfolioHubInputBoundary;
import usecase.portfolio_hub.PortfolioHubInteractor;
import usecase.portfolio_hub.PortfolioHubOutputBoundary;

/**
 * Factory for the Portfolio Hub Use Case.
 */
public final class PortfolioHubUseCaseFactory {
    private PortfolioHubUseCaseFactory() {

    }

    /**
     * Create the Portfolio Hub Controller.
     * @param viewManagerModel The View Manager Model
     * @param portfoliosViewModel The Portfolios View Model
     * @param createViewModel The Create View Model
     * @param dataAccessObject The DAO
     * @return The Portfolio Hub Controller
     */
    public static PortfolioHubController create(
            ViewManagerModel viewManagerModel,
            PortfolioHubViewModel portfoliosViewModel,
            CreateViewModel createViewModel,
            PortfolioHubDataAccessInterface dataAccessObject
    ) {
        PortfolioHubOutputBoundary portfoliosPresenter = new PortfolioHubPresenter(
                viewManagerModel, portfoliosViewModel, createViewModel
        );
        PortfolioHubInputBoundary portfoliosInteractor = new PortfolioHubInteractor(
                portfoliosPresenter, dataAccessObject
        );
        return new PortfolioHubController(portfoliosInteractor);
    }
}
