package interface_adapter.shortsell;

import interface_adapter.ViewManagerModel;
import interface_adapter.portfolio.PortfolioViewModel;
import use_case.shortsell.ShortOutputBoundary;
import use_case.shortsell.ShortOutputData;

public class ShortPresenter implements ShortOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ShortViewModel shortViewModel;
    private final PortfolioViewModel portfolioViewModel;

    public ShortPresenter(ViewManagerModel viewManagerModel,
                          ShortViewModel shortViewModel,
                          PortfolioViewModel portfolioViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.shortViewModel = shortViewModel;
        this.portfolioViewModel = portfolioViewModel;
    }

    @Override
    public void prepareSuccessView(ShortOutputData outputData) {
        ShortState s = shortViewModel.getState();
        s.setError(null);
        shortViewModel.setState(s);
        shortViewModel.firePropertyChanged();
        viewManagerModel.setState(portfolioViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ShortState s = shortViewModel.getState();
        s.setError(errorMessage);
        shortViewModel.setState(s);
        shortViewModel.firePropertyChanged();
    }
}
