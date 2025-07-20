package interface_adapter.portfolio;

import interface_adapter.ViewModel;

/**
 * The View Model for the Portfolio View.
 */
public class PortfolioViewModel extends ViewModel<PortfolioState> {

    public PortfolioViewModel() {
        super("portfolio");
        setState(new PortfolioState());
    }

}