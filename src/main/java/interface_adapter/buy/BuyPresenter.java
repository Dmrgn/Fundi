package interface_adapter.buy;

import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;
import use_case.buy.BuyOutputBoundary;
import use_case.buy.BuyOutputData;

public class BuyPresenter implements BuyOutputBoundary {

    PortfolioState portfolioState;
    PortfolioController portfolioController;
    BuyViewModel buyViewModel;

    public BuyPresenter(BuyViewModel buyViewModel, PortfolioController portfolioController, PortfolioState portfolioState) {
        this.buyViewModel = buyViewModel;
        this.portfolioController = portfolioController;
        this.portfolioState = portfolioState;
    }

    /**
     * Prepares the success view for the Buy Use Case.
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(BuyOutputData outputData) {
        BuyState buyState = buyViewModel.getState();
        buyViewModel.setState(buyState);
        portfolioController.execute(portfolioState.getUsername(),
                portfolioState.getPortfolioId(), portfolioState.getPortfolioName());
    }

    /**
     * Prepares the failure view for the Buy Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final BuyState buyState = buyViewModel.getState();
        buyState.setBuyError(errorMessage);
        buyViewModel.firePropertyChanged();
    }
}
