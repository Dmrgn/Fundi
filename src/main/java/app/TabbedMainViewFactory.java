package app;

import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolioHub.PortfolioHubController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import view.TabbedMainView;
import view.DashboardView;
import view.PortfolioHubView;
import view.NewsView;
import view.WatchlistView;
import view.LeaderboardView;

/**
 * Factory for the Tabbed Main View
 */
public class TabbedMainViewFactory {
    private TabbedMainViewFactory() {

    }

    public static TabbedMainView create(
            MainViewModel mainViewModel,
            PortfolioHubController portfolioHubController,
            NewsController newsController,
            PortfolioController portfolioController,
            NavigationController navigationController,
            SearchController searchController,
            SearchViewModel searchViewModel,
            DashboardView dashboardView,
            PortfolioHubView portfoliosView,
            NewsView newsView,
            WatchlistView watchlistView,
            LeaderboardView leaderboardView) {
        return new TabbedMainView(
                mainViewModel,
                portfolioHubController,
                newsController,
                portfolioController,
                navigationController,
                searchController,
                searchViewModel,
                dashboardView,
                portfoliosView,
                newsView,
                watchlistView,
                leaderboardView);
    }
}
