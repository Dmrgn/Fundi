package app;

import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import view.DashboardView;

/**
 * Factory for creating the Dashboard View.
 */
public class DashboardViewFactory {

    /**
     * Prevents instantiation.
     */
    private DashboardViewFactory() {
    }

    /**
     * Creates a DashboardView with all necessary dependencies.
     * 
     * @param mainViewModel       the main view model
     * @param searchController    the search controller
     * @param searchViewModel     the search view model
     * @param dashboardViewModel  the dashboard view model
     * @param dashboardController the dashboard controller
     * @return the dashboard view
     */
    public static DashboardView create(MainViewModel mainViewModel,
            SearchController searchController,
            SearchViewModel searchViewModel,
            DashboardViewModel dashboardViewModel,
            DashboardController dashboardController) {
        return new DashboardView(mainViewModel, searchController, searchViewModel,
                dashboardViewModel, dashboardController);
    }
}
