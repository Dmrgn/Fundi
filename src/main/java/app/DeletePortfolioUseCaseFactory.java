package app;

import interfaceadapter.portfolio.DeletePortfolioController;
import interfaceadapter.portfolio.DeletePortfolioPresenter;
import interfaceadapter.portfolio_hub.PortfolioHubController;
import usecase.delete_portfolio.DeletePortfolioDataAccessInterface;
import usecase.delete_portfolio.DeletePortfolioInputBoundary;
import usecase.delete_portfolio.DeletePortfolioInteractor;
import usecase.delete_portfolio.DeletePortfolioOutputBoundary;

public final class DeletePortfolioUseCaseFactory {
    private DeletePortfolioUseCaseFactory() {
    }

    public static DeletePortfolioController create(
            PortfolioHubController portfolioHubController,
            DeletePortfolioDataAccessInterface dataAccessObject) {
        DeletePortfolioOutputBoundary presenter = new DeletePortfolioPresenter(portfolioHubController);
        DeletePortfolioInputBoundary interactor = new DeletePortfolioInteractor(dataAccessObject, presenter);
        return new DeletePortfolioController(interactor);
    }
}
