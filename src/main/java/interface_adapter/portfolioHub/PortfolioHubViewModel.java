package interface_adapter.portfolioHub;

import interface_adapter.ViewModel;

/**
 * The View Model for the portfolios View.
 */
public class PortfolioHubViewModel extends ViewModel<PortfolioHubState> {

    public PortfolioHubViewModel() {
        super("portfolio hub");
        setState(new PortfolioHubState());
    }

}