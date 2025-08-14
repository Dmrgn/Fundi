package interfaceadapter.portfolio;

import interfaceadapter.ViewModel;

/**
 * The View Model for the Portfolio View.
 */
public class PortfolioViewModel extends ViewModel<PortfolioState> {

    public PortfolioViewModel() {
        super("portfolio");
        setState(new PortfolioState());
    }

}
