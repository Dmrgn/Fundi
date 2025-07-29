package app;

import interface_adapter.PortfolioViewModelUpdater;
import interface_adapter.ViewManagerModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.sell.SellController;
import interface_adapter.sell.SellInteractor;
import interface_adapter.sell.SellPresenter;
import interface_adapter.sell.SellViewModel;
import use_case.sell.SellInputBoundary;
import use_case.sell.SellOutputBoundary;
import use_case.sell.SellStockDataAccessInterface;
import use_case.sell.SellTransactionDataAccessInterface;

public class SellUseCaseFactory {
    private SellUseCaseFactory() {

    }

    public static SellController create(
            ViewManagerModel viewManagerModel,
            SellViewModel sellViewModel,
            PortfolioViewModel portfolioViewModel,
            PortfolioViewModelUpdater portfolioViewModelUpdater,
            SellStockDataAccessInterface stockDataAccessObject,
            SellTransactionDataAccessInterface transactionDataAccessObject
    ) {
        SellOutputBoundary sellPresenter = new SellPresenter(
                viewManagerModel,
                portfolioViewModel,
                portfolioViewModelUpdater,
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
