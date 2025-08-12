package app;

import interfaceadapter.company_details.CompanyDetailsController;
import interfaceadapter.dashboard.DashboardController;
import interfaceadapter.dashboard.DashboardViewModel;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.navigation.NavigationController;
import interfaceadapter.search.SearchController;
import interfaceadapter.search.SearchViewModel;
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
     * @param mainViewModel            the main view model
     * @param searchController         the search controller
     * @param searchViewModel          the search view model
     * @param dashboardViewModel       the dashboard view model
     * @param dashboardController      the dashboard controller
     * @param navigationController     the navigation controller
     * @param companyDetailsController the company details controller
     * @return the dashboard view
     */
    public static DashboardView create(MainViewModel mainViewModel,
            SearchController searchController,
            SearchViewModel searchViewModel,
            DashboardViewModel dashboardViewModel,
            DashboardController dashboardController,
            NavigationController navigationController,
            CompanyDetailsController companyDetailsController) {
        return new DashboardView(mainViewModel, 
                searchController, 
                searchViewModel,
                dashboardViewModel,
                dashboardController,
                navigationController,
                companyDetailsController);
    }
}
