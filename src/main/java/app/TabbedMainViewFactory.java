package app;

import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolios.PortfoliosController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import view.TabbedMainView;
import view.PortfoliosView;
import view.NewsView;
import view.WatchlistView;
import view.LeaderboardView;

public class TabbedMainViewFactory {
    private TabbedMainViewFactory() {

    }

    public static TabbedMainView create(
            MainViewModel mainViewModel,
            PortfoliosController portfoliosController,
            NewsController newsController,
            PortfolioController portfolioController,
            NavigationController navigationController,
            SearchController searchController,
            SearchViewModel searchViewModel,
            PortfoliosView portfoliosView,
            NewsView newsView,
            WatchlistView watchlistView,
            LeaderboardView leaderboardView) {
        return new TabbedMainView(
                mainViewModel,
                portfoliosController,
                newsController,
                portfolioController,
                navigationController,
                searchController,
                searchViewModel,
                portfoliosView,
                newsView,
                watchlistView,
                leaderboardView);
    }
}
