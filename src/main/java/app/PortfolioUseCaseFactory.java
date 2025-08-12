package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.buy.BuyViewModel;
import interfaceadapter.navigation.NavigationController;
import interfaceadapter.portfolio.PortfolioController;
import interfaceadapter.portfolio.PortfolioPresenter;
import interfaceadapter.portfolio.PortfolioViewModel;
import interfaceadapter.sell.SellViewModel;
import usecase.portfolio.PortfolioInputBoundary;
import usecase.portfolio.PortfolioInteractor;
import usecase.portfolio.PortfolioOutputBoundary;
import usecase.portfolio.PortfolioStockDataAccessInterface;
import usecase.portfolio.PortfolioTransactionDataAccessInterface;

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
