package interface_adapter.portfolios;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateState;
import interface_adapter.create.CreateViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;
import use_case.main.MainOutputBoundary;
import use_case.main.MainOutputData;
import use_case.portfolios.PortfoliosOutputBoundary;
import use_case.portfolios.PortfoliosOutputData;
import view.PortfolioView;

/**
 * The Presenter for the portfolios Use Case.
 */
public class PortfoliosPresenter implements PortfoliosOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PortfoliosViewModel portfoliosViewModel;
    private final CreateViewModel createViewModel;

    public PortfoliosPresenter(ViewManagerModel viewManagerModel, PortfoliosViewModel portfoliosViewModel, CreateViewModel createViewModel)  {
        this.viewManagerModel = viewManagerModel;
        this.portfoliosViewModel = portfoliosViewModel;
        this.createViewModel = createViewModel;
    }

    @Override
    public void prepareView(PortfoliosOutputData portfoliosOutputData) {
        final PortfoliosState portfoliosState = portfoliosViewModel.getState();
        portfoliosState.setUsername(portfoliosOutputData.getUsername());
        portfoliosState.setPortfolios(portfoliosOutputData.getPortfolios());

        this.portfoliosViewModel.setState(portfoliosState);
        this.portfoliosViewModel.firePropertyChanged();

        this.viewManagerModel.setState(portfoliosViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void routeToCreate(String username) {
        CreateState state = createViewModel.getState();
        state.setUsername(username);
        createViewModel.setState(state);
        createViewModel.firePropertyChanged();
        viewManagerModel.setState("create");
        viewManagerModel.firePropertyChanged();
    }
}