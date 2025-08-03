package app;

import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolioHub.PortfolioHubController;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import view.MainView;


public class MainViewFactory {
    private MainViewFactory() {

    }

    public static MainView create(
            MainViewModel mainViewModel,
            PortfolioHubController portfolioHubController,
            NewsController newsController,
            NavigationController navigationController,
            SearchController searchController,
            SearchViewModel searchViewModel
    ) {
        return new MainView(
                mainViewModel,
                portfolioHubController,
                newsController,
                navigationController,
                searchController,
                searchViewModel
        );
    }
}
