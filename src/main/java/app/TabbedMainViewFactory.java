package app;

import interfaceadapter.dashboard.DashboardController;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.news.NewsController;
import interfaceadapter.portfolio_hub.PortfolioHubController;
import interfaceadapter.portfolio.PortfolioController;
import interfaceadapter.navigation.NavigationController;
import interfaceadapter.search.SearchController;
import interfaceadapter.search.SearchViewModel;
import view.*;

/**
 * Factory for the Tabbed Main View
 */
public class TabbedMainViewFactory {
    private TabbedMainViewFactory() {

    }

    public static TabbedMainView create(
            MainViewModel mainViewModel,
            PortfolioHubController portfolioHubController,
            DashboardController dashboardController,
            NewsController newsController,
            PortfolioController portfolioController,
            NavigationController navigationController,
            SearchController searchController,
            SearchViewModel searchViewModel,
            DashboardView dashboardView,
            PortfolioHubView portfoliosView,
            NewsView newsView,
            WatchlistView watchlistView,
            LeaderboardView leaderboardView,
            SettingsView settingsView) {
        return new TabbedMainView(
                mainViewModel,
                portfolioHubController,
                dashboardController,
                newsController,
                portfolioController,
                navigationController,
                searchController,
                searchViewModel,
                dashboardView,
                portfoliosView,
                newsView,
                watchlistView,
                leaderboardView,
                settingsView);
    }
}
