package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.portfolio.PortfolioController;
import use_case.portfolio.PortfolioInteractor;
import interface_adapter.portfolio.PortfolioPresenter;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.sell.SellViewModel;
import use_case.portfolio.PortfolioStockDataAccessInterface;
import use_case.portfolio.PortfolioTransactionDataAccessInterface;
import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioOutputBoundary;
import interface_adapter.navigation.NavigationController;

/**
 * Factory for the Portfolio Use Case
 */
public class PortfolioUseCaseFactory {
        private PortfolioUseCaseFactory() {

        }

        public static PortfolioController create(
                        ViewManagerModel viewManagerModel,
                        PortfolioViewModel portfolioViewModel,
                        BuyViewModel buyViewModel,
                        SellViewModel sellViewModel,
                        PortfolioTransactionDataAccessInterface transactionDataAccessObject,
                        PortfolioStockDataAccessInterface stockDataAccessObject,
                        NavigationController navigationController) {
                PortfolioOutputBoundary portfolioPresenter = new PortfolioPresenter(
                                viewManagerModel,
                                portfolioViewModel,
                                buyViewModel,
                                sellViewModel,
                                navigationController);
                PortfolioInputBoundary portfolioInteractor = new PortfolioInteractor(
                                transactionDataAccessObject, stockDataAccessObject, portfolioPresenter);
                return new PortfolioController(portfolioInteractor);
        }
}
