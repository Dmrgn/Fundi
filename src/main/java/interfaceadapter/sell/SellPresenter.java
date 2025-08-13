package interfaceadapter.sell;

import interfaceadapter.PortfolioCommand;
import interfaceadapter.PortfolioUpdateCommand;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.portfolio.PortfolioViewModel;
import usecase.sell.SellOutputBoundary;
import usecase.sell.SellOutputData;

/**
 * The Presenter for the Sell Use Case.
 */
public class SellPresenter implements SellOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final PortfolioViewModel portfolioViewModel;
    private final SellViewModel sellViewModel;

    public SellPresenter(ViewManagerModel viewManagerModel, PortfolioViewModel portfolioViewModel,
            SellViewModel sellViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.portfolioViewModel = portfolioViewModel;
        this.sellViewModel = sellViewModel;
    }

    /**
     * Prepares the success view for the Sell Use Case.
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(SellOutputData outputData) {
        final PortfolioCommand cmd = new PortfolioUpdateCommand(
                outputData.getTicker(),
                // negative => sell
                -outputData.getPrice(),
                outputData.getQuantity());
        cmd.execute(portfolioViewModel);
        // Reflect new balance in the sell view
        SellState state = sellViewModel.getState();
        state.setSellError(null);
        state.setBalance(outputData.getBalance());
        sellViewModel.setState(state);

        // Update portfolio state balance for real-time refresh
        portfolioViewModel.getState().setBalance(outputData.getBalance());

        portfolioViewModel.firePropertyChanged();
        viewManagerModel.setState(portfolioViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
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
