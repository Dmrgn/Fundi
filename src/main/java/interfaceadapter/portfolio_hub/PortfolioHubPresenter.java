package interfaceadapter.portfolio_hub;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.create.CreateState;
import interfaceadapter.create.CreateViewModel;
import usecase.portfolio_hub.PortfolioHubOutputBoundary;
import usecase.portfolio_hub.PortfolioHubOutputData;

/**
 * The Presenter for the Portfolio Hub Use Case.
 */
public class PortfolioHubPresenter implements PortfolioHubOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PortfolioHubViewModel portfoliosViewModel;
    private final CreateViewModel createViewModel;

    public PortfolioHubPresenter(ViewManagerModel viewManagerModel, PortfolioHubViewModel portfoliosViewModel,
            CreateViewModel createViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.portfoliosViewModel = portfoliosViewModel;
        this.createViewModel = createViewModel;
    }

    /**
     * Prepare the portfolio hub view.
     * 
     * @param portfoliosOutputData the output data
     */
    @Override
    public void prepareView(PortfolioHubOutputData portfoliosOutputData) {
        final PortfolioHubState portfoliosState = portfoliosViewModel.getState();
        portfoliosState.setUsername(portfoliosOutputData.getUsername());
        portfoliosState.setPortfolios(portfoliosOutputData.getPortfolios());

        this.portfoliosViewModel.setState(portfoliosState);
        this.portfoliosViewModel.firePropertyChanged();

        this.viewManagerModel.setState("tabbedmain");
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * Switch to the Create View.
     * 
     * @param username The username to update the state of the Create View Model
     */
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