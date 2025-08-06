package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateViewModel;
import interface_adapter.portfolio_hub.PortfolioHubController;
import interface_adapter.portfolio_hub.PortfolioHubPresenter;
import interface_adapter.portfolio_hub.PortfolioHubViewModel;
import use_case.portfolio_hub.PortfolioHubDataAccessInterface;
import use_case.portfolio_hub.PortfolioHubInputBoundary;
import use_case.portfolio_hub.PortfolioHubInteractor;
import use_case.portfolio_hub.PortfolioHubOutputBoundary;

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
