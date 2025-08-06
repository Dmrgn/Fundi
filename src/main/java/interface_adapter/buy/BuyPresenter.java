package interface_adapter.buy;

import interface_adapter.PortfolioViewModelUpdater;
import interface_adapter.ViewManagerModel;
import interface_adapter.portfolio.PortfolioViewModel;
import use_case.buy.BuyOutputBoundary;
import use_case.buy.BuyOutputData;

/**
 * Presenter for the Buy Use Case.
 */
public class BuyPresenter implements BuyOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PortfolioViewModel portfolioViewModel;
    private final PortfolioViewModelUpdater updater;
    private final BuyViewModel buyViewModel;

    public BuyPresenter(ViewManagerModel viewManagerModel, BuyViewModel buyViewModel, PortfolioViewModelUpdater updater, PortfolioViewModel portfolioViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.buyViewModel = buyViewModel;
        this.updater = updater;
        this.portfolioViewModel = portfolioViewModel;
    }

    /**
     * Prepares the success view for the Buy Use Case.
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(BuyOutputData outputData) {
        updater.update(portfolioViewModel, outputData.getTicker(), outputData.getPrice(), outputData.getQuantity());
        portfolioViewModel.firePropertyChanged();
        viewManagerModel.setState(portfolioViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Buy Use Case.
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final BuyState buyState = buyViewModel.getState();
        buyState.setBuyError(errorMessage);
        buyViewModel.firePropertyChanged();
    }
}
