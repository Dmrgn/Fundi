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
                -outputData.getPrice(), // negative => sell
                outputData.getQuantity());
        cmd.execute(portfolioViewModel);
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
