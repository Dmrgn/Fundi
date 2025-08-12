package interfaceadapter.dashboard;

import usecase.dashboard.DashboardOutputBoundary;
import usecase.dashboard.DashboardOutputData;

/**
 * The Presenter for the Dashboard Use Case.
 */
public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;

    public DashboardPresenter(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void prepareSuccessView(DashboardOutputData outputData) {
        final DashboardState dashboardState = dashboardViewModel.getState();
        dashboardState.setPortfolioValueHistory(outputData.getPortfolioValueHistory());
        dashboardState.setErrorMessage("");
        dashboardViewModel.setState(dashboardState);
        dashboardViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final DashboardState dashboardState = dashboardViewModel.getState();
        dashboardState.setErrorMessage(errorMessage);
        dashboardViewModel.setState(dashboardState);
        dashboardViewModel.firePropertyChanged();
    }
}
