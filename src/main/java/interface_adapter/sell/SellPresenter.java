package interface_adapter.sell;

import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;
import use_case.sell.SellOutputBoundary;
import use_case.sell.SellOutputData;

public class SellPresenter implements SellOutputBoundary {
    PortfolioState portfolioState;
    PortfolioController portfolioController;
    SellViewModel sellViewModel;

    public SellPresenter(SellViewModel sellViewModel, PortfolioController portfolioController, PortfolioState portfolioState) {
        this.sellViewModel = sellViewModel;
        this.portfolioController = portfolioController;
        this.portfolioState = portfolioState;
    }

    /**
     * Prepares the success view for the Sell Use Case.
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(SellOutputData outputData) {
        SellState sellState = sellViewModel.getState();
        sellState.setSellError("");
        sellViewModel.setState(sellState);
        portfolioController.execute(portfolioState.getUsername(),
                portfolioState.getPortfolioId(), portfolioState.getPortfolioName());
    }

    /**
     * Prepares the failure view for the Sell Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final SellState sellState = sellViewModel.getState();
        sellState.setSellError(errorMessage);
        sellViewModel.firePropertyChanged();
    }
}
