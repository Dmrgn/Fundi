package interfaceadapter.portfolio_hub;

import interfaceadapter.ViewModel;

/**
 * The View Model for the Portfolio Hub View.
 */
public class PortfolioHubViewModel extends ViewModel<PortfolioHubState> {

    public PortfolioHubViewModel() {
        super("portfolio hub");
        setState(new PortfolioHubState());
    }

}