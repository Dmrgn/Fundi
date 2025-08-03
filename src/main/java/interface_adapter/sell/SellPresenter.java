package interface_adapter.sell;

import interface_adapter.PortfolioViewModelUpdater;
import interface_adapter.ViewManagerModel;
import interface_adapter.portfolio.PortfolioViewModel;
import use_case.sell.SellOutputBoundary;
import use_case.sell.SellOutputData;

/**
 * The Presenter for the Sell Use Case
 */
public class SellPresenter implements SellOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final PortfolioViewModel portfolioViewModel;
    private final PortfolioViewModelUpdater updater;
    private final SellViewModel sellViewModel;

    public SellPresenter(ViewManagerModel viewManagerModel, PortfolioViewModel portfolioViewModel, PortfolioViewModelUpdater updater, SellViewModel sellViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.portfolioViewModel = portfolioViewModel;
        this.updater = updater;
        this.sellViewModel = sellViewModel;
    }


    /**
     * Prepares the success view for the Sell Use Case.
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(SellOutputData outputData) {
        updater.update(portfolioViewModel,
                outputData.getTicker(), outputData.getPrice(), outputData.getQuantity());
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
