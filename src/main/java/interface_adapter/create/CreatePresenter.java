package interface_adapter.create;

import interface_adapter.ViewManagerModel;
import interface_adapter.portfolioHub.PortfolioHubState;
import interface_adapter.portfolioHub.PortfolioHubViewModel;
import use_case.create.CreateOutputBoundary;
import use_case.create.CreateOutputData;

/**
 * The Presenter for the Create Use Case.
 */
public class CreatePresenter implements CreateOutputBoundary {

    private final CreateViewModel createViewModel;
    private final PortfolioHubViewModel portfoliosViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreatePresenter(ViewManagerModel viewManagerModel,
                          PortfolioHubViewModel portfoliosViewModel,
                          CreateViewModel createViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.portfoliosViewModel = portfoliosViewModel;
        this.createViewModel = createViewModel;
    }

    @Override
    public void prepareSuccessView(CreateOutputData response) {
        // On success, switch to the logged in view.

        final PortfolioHubState portfoliosState = portfoliosViewModel.getState();
        portfoliosState.setUsername(response.getUsername());
        portfoliosState.setPortfolios(response.getPortfolios());
        this.portfoliosViewModel.setState(portfoliosState);
        this.portfoliosViewModel.firePropertyChanged();

        this.viewManagerModel.setState(portfoliosViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final CreateState createState = createViewModel.getState();
        createState.setCreateError(error);
        createViewModel.firePropertyChanged();
    }
}