package interface_adapter.main;

import interface_adapter.ViewManagerModel;
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;
import use_case.main.MainOutputBoundary;
import use_case.main.MainOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class MainPresenter implements MainOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PortfoliosViewModel portfoliosViewModel;

    public MainPresenter(ViewManagerModel viewManagerModel, PortfoliosViewModel portfoliosViewModel)  {
        this.viewManagerModel = viewManagerModel;
        this.portfoliosViewModel = portfoliosViewModel;
    }

    @Override
    public void prepareView(MainOutputData mainOutputData) {
        switch (mainOutputData.getUseCase()) {
            case "Portfolios":
                final PortfoliosState portfoliosState = portfoliosViewModel.getState();
                portfoliosState.setPortfolios(mainOutputData.getPortfolios());
                portfoliosState.setUsername(mainOutputData.getUsername());
                this.portfoliosViewModel.setState(portfoliosState);
                this.portfoliosViewModel.firePropertyChanged();

                this.viewManagerModel.setState(portfoliosViewModel.getViewName());
                this.viewManagerModel.firePropertyChanged();

        }
    }
}