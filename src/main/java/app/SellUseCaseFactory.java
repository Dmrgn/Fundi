package app;

import interface_adapter.PortfolioUpdateCommand;
import interface_adapter.ViewManagerModel;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.sell.SellController;
import interface_adapter.sell.SellPresenter;
import interface_adapter.sell.SellViewModel;
import use_case.sell.SellInputBoundary;
import use_case.sell.SellInteractor;
import use_case.sell.SellOutputBoundary;
import use_case.sell.SellStockDataAccessInterface;
import use_case.sell.SellTransactionDataAccessInterface;

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
