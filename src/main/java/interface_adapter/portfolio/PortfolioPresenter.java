package interface_adapter.portfolio;

import interface_adapter.ViewManagerModel;
import interface_adapter.buy.BuyState;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.sell.SellState;
import interface_adapter.sell.SellViewModel;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.portfolio.PortfolioOutputData;

/**
 * The Presenter for the portfolio Use Case.
 */
public class PortfolioPresenter implements PortfolioOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PortfolioViewModel portfolioViewModel;
    private final BuyViewModel buyViewModel;
    private final SellViewModel sellViewModel;
    private final NavigationController navigationController;

    public PortfolioPresenter(ViewManagerModel viewManagerModel, PortfolioViewModel portfolioViewModel,
            BuyViewModel buyViewModel, SellViewModel sellViewModel, NavigationController navigationController) {
        this.viewManagerModel = viewManagerModel;
        this.portfolioViewModel = portfolioViewModel;
        this.buyViewModel = buyViewModel;
        this.sellViewModel = sellViewModel;
        this.navigationController = navigationController;
    }

    /**
     * Prepare the view for the portfolio use case.
     * @param portfolioOutputData the output data
     */
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

        navigationController.navigateTo(viewManagerModel.getState(), "portfolio");

        this.viewManagerModel.setState(portfolioViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * Switch to the Buy View.
     * @param portfolioId Set the state information for the Buy View Model
     */
    @Override
    public void routeToBuy(String portfolioId) {
        BuyState state = buyViewModel.getState();
        state.setPortfolioId(portfolioId);
        buyViewModel.setState(state);
        buyViewModel.firePropertyChanged();
        // Push to navigation stack so can navigate back to Portfolio page
        navigationController.navigateTo(viewManagerModel.getState(), "buy");
        viewManagerModel.setState("buy");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Switch to the Sell View.
     * @param portfolioId Set the state information for the Sell View Model
     */
    @Override
    public void routeToSell(String portfolioId) {
        SellState state = sellViewModel.getState();
        state.setPortfolioId(portfolioId);
        sellViewModel.setState(state);
        sellViewModel.firePropertyChanged();
        navigationController.navigateTo(viewManagerModel.getState(), "sell");
        viewManagerModel.setState("sell");
        viewManagerModel.firePropertyChanged();
    }
}