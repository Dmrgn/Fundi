package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateViewModel;
import interface_adapter.portfolioHub.PortfolioHubController;
import use_case.portfolioHub.PortfolioHubInteractor;
import interface_adapter.portfolioHub.PortfolioHubPresenter;
import interface_adapter.portfolioHub.PortfolioHubViewModel;
import use_case.portfolioHub.PortfolioHubDataAccessInterface;
import use_case.portfolioHub.PortfolioHubInputBoundary;
import use_case.portfolioHub.PortfolioHubOutputBoundary;

public class PortfolioHubUseCaseFactory {
    private PortfolioHubUseCaseFactory() {

    }

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
