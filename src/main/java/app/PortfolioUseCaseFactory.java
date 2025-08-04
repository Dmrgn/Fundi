package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioPresenter;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.sell.SellViewModel;
import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioInteractor;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.portfolio.PortfolioStockDataAccessInterface;
import use_case.portfolio.PortfolioTransactionDataAccessInterface;

/**
 * Factory for the Portfolio Use Case.
 */
public final class PortfolioUseCaseFactory {
    private PortfolioUseCaseFactory() {

    }

    /**
    * Create the Portfolio Controller.
    * @param viewManagerModel The View Manager Model
    * @param portfolioViewModel The Portfolio View Model
    * @param buyViewModel The Buy View Model
    * @param sellViewModel The Sell View Model
    * @param transactionDataAccessObject The Transaction DAO
    * @param stockDataAccessObject The Stock DAO
    * @param navigationController The Navigation Controller
    * @return The Portfolio Controller
    */
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
