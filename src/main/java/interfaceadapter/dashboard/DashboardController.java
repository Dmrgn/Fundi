package interfaceadapter.dashboard;

import usecase.dashboard.DashboardInputBoundary;
import usecase.dashboard.DashboardInputData;

/**
 * The controller for the Dashboard Use Case.
 */
public class DashboardController {
    private final DashboardInputBoundary dashboardUseCaseInteractor;

    public DashboardController(DashboardInputBoundary dashboardUseCaseInteractor) {
        this.dashboardUseCaseInteractor = dashboardUseCaseInteractor;
    }

    /**
     * Executes the Dashboard Use Case.
     * 
     * @param username the username of the user
     */
    public void execute(String username) {
        final DashboardInputData dashboardInputData = new DashboardInputData(username);
        dashboardUseCaseInteractor.execute(dashboardInputData);
    }
}
