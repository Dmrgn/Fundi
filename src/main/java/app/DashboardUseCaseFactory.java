package app;

import dataaccess.DBDashboardDataAccessObject;
import interfaceadapter.dashboard.DashboardController;
import interfaceadapter.dashboard.DashboardPresenter;
import interfaceadapter.dashboard.DashboardViewModel;
import usecase.dashboard.DashboardInputBoundary;
import usecase.dashboard.DashboardInteractor;
import usecase.dashboard.DashboardOutputBoundary;

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
