package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.buy.BuyController;
import interfaceadapter.buy.BuyPresenter;
import interfaceadapter.buy.BuyViewModel;
import interfaceadapter.portfolio.PortfolioViewModel;
import usecase.buy.BuyInputBoundary;
import usecase.buy.BuyInteractor;
import usecase.buy.BuyOutputBoundary;
import usecase.buy.BuyStockDataAccessInterface;
import usecase.buy.BuyTransactionDataAccessInterface;

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
