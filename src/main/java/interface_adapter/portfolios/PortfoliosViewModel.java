package interface_adapter.portfolios;

import interface_adapter.ViewModel;

/**
 * The View Model for the portfolios View.
 */
public class PortfoliosViewModel extends ViewModel<PortfoliosState> {

    public PortfoliosViewModel() {
        super("portfolios");
        setState(new PortfoliosState());
    }

}