package interface_adapter.portfolio_hub;

import interface_adapter.ViewModel;

/**
 * The View Model for the Portfolio Hub View.
 */
public class PortfolioHubViewModel extends ViewModel<PortfolioHubState> {

    public PortfolioHubViewModel() {
        super("portfolio hub");
        setState(new PortfolioHubState());
    }

}