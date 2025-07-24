package interface_adapter.portfolio;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateState;
import interface_adapter.create.CreateViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;
import use_case.main.MainOutputBoundary;
import use_case.main.MainOutputData;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.portfolio.PortfolioOutputData;
import use_case.portfolios.PortfoliosOutputBoundary;
import use_case.portfolios.PortfoliosOutputData;
import view.PortfolioView;

/**
 * The Presenter for the portfolio Use Case.
 */
public class PortfolioPresenter implements PortfolioOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PortfolioViewModel portfolioViewModel;

    public PortfolioPresenter(ViewManagerModel viewManagerModel, PortfolioViewModel portfolioViewModel)  {
        this.viewManagerModel = viewManagerModel;
        this.portfolioViewModel = portfolioViewModel;
    }

    @Override
    public void prepareView(PortfolioOutputData portfolioOutputData) {
        final PortfolioState portfolioState = portfolioViewModel.getState();
        portfolioState.setUsername(portfolioOutputData.getUsername());
        portfolioState.setPortfolioId(portfolioOutputData.getPortfolioId());
        portfolioState.setPortfolioName(portfolioOutputData.getPortfolioName());
        portfolioState.setStockNames(portfolioOutputData.getStockNames());
        portfolioState.setStockAmounts(portfolioOutputData.getStockAmounts());
        portfolioState.setStockPrices(portfolioOutputData.getStockPrices());

        this.portfolioViewModel.setState(portfolioState);
        this.portfolioViewModel.firePropertyChanged();

        this.viewManagerModel.setState(portfolioViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}