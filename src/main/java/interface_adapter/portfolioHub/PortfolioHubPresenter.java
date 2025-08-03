package interface_adapter.portfolioHub;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateState;
import interface_adapter.create.CreateViewModel;
import use_case.portfolioHub.PortfolioHubOutputBoundary;
import use_case.portfolioHub.PortfolioHubOutputData;

/**
 * The Presenter for the portfolios Use Case.
 */
public class PortfolioHubPresenter implements PortfolioHubOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PortfolioHubViewModel portfoliosViewModel;
    private final CreateViewModel createViewModel;

    public PortfolioHubPresenter(ViewManagerModel viewManagerModel, PortfolioHubViewModel portfoliosViewModel, CreateViewModel createViewModel)  {
        this.viewManagerModel = viewManagerModel;
        this.portfoliosViewModel = portfoliosViewModel;
        this.createViewModel = createViewModel;
    }

    @Override
    public void prepareView(PortfolioHubOutputData portfoliosOutputData) {
        final PortfolioHubState portfoliosState = portfoliosViewModel.getState();
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