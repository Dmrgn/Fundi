package app;

import interface_adapter.PortfolioViewModelUpdater;
import interface_adapter.ViewManagerModel;
import interface_adapter.buy.BuyController;
import use_case.buy.BuyInteractor;
import interface_adapter.buy.BuyPresenter;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.portfolio.PortfolioViewModel;
import use_case.buy.BuyInputBoundary;
import use_case.buy.BuyOutputBoundary;
import use_case.buy.BuyStockDataAccessInterface;
import use_case.buy.BuyTransactionDataAccessInterface;

public class BuyUseCaseFactory {
    private BuyUseCaseFactory() {

    }

    public static BuyController create(
            ViewManagerModel viewManagerModel,
            BuyViewModel buyViewModel,
            PortfolioViewModelUpdater portfolioViewModelUpdater,
            PortfolioViewModel portfolioViewModel,
            BuyStockDataAccessInterface stockDataAccessObject,
            BuyTransactionDataAccessInterface transactionDataAccessObject
    ) {
        BuyOutputBoundary buyPresenter = new BuyPresenter(
            viewManagerModel,
            buyViewModel,
            portfolioViewModelUpdater,
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
