package interface_adapter.portfolio;

import interface_adapter.ViewManagerModel;
import interface_adapter.buy.BuyState;
import interface_adapter.buy.BuyViewModel;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.portfolio.PortfolioOutputData;

/**
 * The Presenter for the portfolio Use Case.
 */
public class PortfolioPresenter implements PortfolioOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PortfolioViewModel portfolioViewModel;
    private final BuyViewModel buyViewModel;

    public PortfolioPresenter(ViewManagerModel viewManagerModel, PortfolioViewModel portfolioViewModel, BuyViewModel buyViewModel)  {
        this.viewManagerModel = viewManagerModel;
        this.portfolioViewModel = portfolioViewModel;
        this.buyViewModel = buyViewModel;
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

    @Override
    public void routeToBuy(String portfolioId) {
        BuyState state = buyViewModel.getState();
        state.setPortfolioId(portfolioId);
        buyViewModel.setState(state);
        buyViewModel.firePropertyChanged();
        viewManagerModel.setState("buy");
        viewManagerModel.firePropertyChanged();
    }
}