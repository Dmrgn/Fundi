package interface_adapter.portfolio;

import interface_adapter.ViewManagerModel;
import interface_adapter.buy.BuyState;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.sell.SellState;
import interface_adapter.sell.SellViewModel;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.portfolio.PortfolioOutputData;
import view.SellView;

/**
 * The Presenter for the portfolio Use Case.
 */
public class PortfolioPresenter implements PortfolioOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PortfolioViewModel portfolioViewModel;
    private final BuyViewModel buyViewModel;
    private final SellViewModel sellViewModel;

    public PortfolioPresenter(ViewManagerModel viewManagerModel, PortfolioViewModel portfolioViewModel, BuyViewModel buyViewModel, SellViewModel sellViewModel)  {
        this.viewManagerModel = viewManagerModel;
        this.portfolioViewModel = portfolioViewModel;
        this.buyViewModel = buyViewModel;
        this.sellViewModel = sellViewModel;
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

    @Override
    public void routeToSell(String portfolioId) {
        SellState state = sellViewModel.getState();
        state.setPortfolioId(portfolioId);
        sellViewModel.setState(state);
        sellViewModel.firePropertyChanged();
        viewManagerModel.setState("sell");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void routeToPortfolios() {
        viewManagerModel.setState("portfolios");
        viewManagerModel.firePropertyChanged();
    }
}