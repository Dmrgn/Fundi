package app;

import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyInteractor;
import interface_adapter.buy.BuyPresenter;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;
import use_case.buy.BuyInputBoundary;
import use_case.buy.BuyOutputBoundary;
import use_case.buy.BuyStockDataAccessInterface;
import use_case.buy.BuyTransactionDataAccessInterface;

public class BuyUseCaseFactory {
    private BuyUseCaseFactory() {

    }

    public static BuyController create(
            BuyViewModel buyViewModel,
            PortfolioController portfolioController,
            PortfolioState portfolioState,
            BuyStockDataAccessInterface stockDataAccessObject,
            BuyTransactionDataAccessInterface transactionDataAccessObject
    ) {
        BuyOutputBoundary buyPresenter = new BuyPresenter(
            buyViewModel,
            portfolioController,
            portfolioState
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
