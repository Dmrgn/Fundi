package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.portfolio.PortfolioViewModel;
import interfaceadapter.sell.SellController;
import interfaceadapter.sell.SellPresenter;
import interfaceadapter.sell.SellViewModel;
import usecase.sell.SellInputBoundary;
import usecase.sell.SellInteractor;
import usecase.sell.SellOutputBoundary;
import usecase.sell.SellStockDataAccessInterface;
import usecase.sell.SellTransactionDataAccessInterface;

/**
 * Factory for the Sell Use Case.
 */
public final class SellUseCaseFactory {
    private SellUseCaseFactory() {

    }

    /**
     * Create the Sell Controller.
     * @param viewManagerModel The View Manager Model
     * @param sellViewModel The Sell View Model
     * @param portfolioViewModel The Portfolio View Model
     * @param stockDataAccessObject The Stock DAO
     * @param transactionDataAccessObject The Transaction DAO
     * @return The Sell Controller
     */
    public static SellController create(
            ViewManagerModel viewManagerModel,
            SellViewModel sellViewModel,
            PortfolioViewModel portfolioViewModel,
            SellStockDataAccessInterface stockDataAccessObject,
            SellTransactionDataAccessInterface transactionDataAccessObject
    ) {
        SellOutputBoundary sellPresenter = new SellPresenter(
                viewManagerModel,
                portfolioViewModel,
                sellViewModel
        );
        SellInputBoundary sellInteractor = new SellInteractor(
                stockDataAccessObject,
                transactionDataAccessObject,
                sellPresenter
        );
        return new SellController(sellInteractor);
    }
}
