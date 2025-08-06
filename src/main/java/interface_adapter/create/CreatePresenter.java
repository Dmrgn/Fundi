package interface_adapter.create;

import interface_adapter.ViewManagerModel;
import interface_adapter.portfolio_hub.PortfolioHubState;
import interface_adapter.portfolio_hub.PortfolioHubViewModel;
import use_case.create.CreateOutputBoundary;
import use_case.create.CreateOutputData;

/**
 * The Presenter for the Create Use Case.
 */
public class CreatePresenter implements CreateOutputBoundary {

    private final CreateViewModel createViewModel;
    private final PortfolioHubViewModel portfolioHubViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreatePresenter(ViewManagerModel viewManagerModel,
                          PortfolioHubViewModel portfolioHubViewModel,
                          CreateViewModel createViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.portfolioHubViewModel = portfolioHubViewModel;
        this.createViewModel = createViewModel;
    }

    /**
     * Prepare the success view.
     * @param response the output data for the success view
     */
    @Override
    public void prepareSuccessView(CreateOutputData response) {

        final PortfolioHubState portfoliosState = portfolioHubViewModel.getState();
        portfoliosState.setUsername(response.getUsername());
        portfoliosState.setPortfolios(response.getPortfolios());
        this.portfolioHubViewModel.setState(portfoliosState);
        this.portfolioHubViewModel.firePropertyChanged();

        this.viewManagerModel.setState(portfolioHubViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view.
     * @param error the explanation of the failure
     */
    @Override
    public void prepareFailView(String error) {
        final CreateState createState = createViewModel.getState();
        createState.setCreateError(error);
        createViewModel.firePropertyChanged();
    }
}