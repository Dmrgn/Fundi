package interface_adapter.buy;

import interface_adapter.PortfolioCommand;
import interface_adapter.PortfolioUpdateCommand;
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
    private final BuyViewModel buyViewModel;

    public BuyPresenter(ViewManagerModel viewManagerModel, BuyViewModel buyViewModel, PortfolioViewModel portfolioViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.buyViewModel = buyViewModel;
        this.portfolioViewModel = portfolioViewModel;
    }

    /**
     * Prepares the success view for the Buy Use Case.
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(BuyOutputData outputData) {
        final PortfolioCommand cmd = new PortfolioUpdateCommand(outputData.getTicker(), outputData.getPrice(),
                outputData.getQuantity());
        cmd.execute(portfolioViewModel);
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
