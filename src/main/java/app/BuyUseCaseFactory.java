package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyPresenter;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.portfolio.PortfolioViewModel;
import use_case.buy.BuyInputBoundary;
import use_case.buy.BuyInteractor;
import use_case.buy.BuyOutputBoundary;
import use_case.buy.BuyStockDataAccessInterface;
import use_case.buy.BuyTransactionDataAccessInterface;

/**
 * Factory for the Buy Use Case.
 */
public final class BuyUseCaseFactory {
    private BuyUseCaseFactory() {

    }

    /**
     * Create the Buy Controller.
     * @param viewManagerModel The View Manager Model
     * @param buyViewModel The Buy View Model
     * @param portfolioViewModel The Portfolio View Model
     * @param stockDataAccessObject The Stock DAO
     * @param transactionDataAccessObject The Transaction DAO
     * @return The Buy Controller
     */
    public static BuyController create(
            ViewManagerModel viewManagerModel,
            BuyViewModel buyViewModel,
            PortfolioViewModel portfolioViewModel,
            BuyStockDataAccessInterface stockDataAccessObject,
            BuyTransactionDataAccessInterface transactionDataAccessObject
    ) {
        BuyOutputBoundary buyPresenter = new BuyPresenter(
            viewManagerModel,
            buyViewModel,
            portfolioViewModel
        );
        BuyInputBoundary buyInteractor = new BuyInteractor(
                stockDataAccessObject,
                transactionDataAccessObject,
                buyPresenter
        );
        return new BuyController(
                buyInteractor
        );
    }
}
