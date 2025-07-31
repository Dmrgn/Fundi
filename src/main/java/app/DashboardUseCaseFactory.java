package app;

import data_access.DBDashboardDataAccessObject;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardPresenter;
import interface_adapter.dashboard.DashboardViewModel;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInteractor;
import use_case.dashboard.DashboardOutputBoundary;

import java.sql.SQLException;

/**
 * Factory for creating Dashboard use case components.
 */
public class DashboardUseCaseFactory {

    /**
     * Prevents instantiation.
     */
    private DashboardUseCaseFactory() {
    }

    /**
     * Creates a DashboardController with all necessary dependencies.
     * 
     * @param dashboardViewModel the dashboard view model
     * @return the dashboard controller
     */
    public static DashboardController createDashboardController(DashboardViewModel dashboardViewModel) {
        try {
            final DashboardOutputBoundary dashboardOutputBoundary = new DashboardPresenter(dashboardViewModel);
            final DBDashboardDataAccessObject dashboardDataAccessObject = new DBDashboardDataAccessObject();
            final DashboardInputBoundary dashboardInteractor = new DashboardInteractor(
                    dashboardDataAccessObject, dashboardOutputBoundary);

            return new DashboardController(dashboardInteractor);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create dashboard controller: " + e.getMessage(), e);
        }
    }
}
