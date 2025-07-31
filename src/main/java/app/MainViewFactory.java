package app;

import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolios.PortfoliosController;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import view.MainView;


public class MainViewFactory {
    private MainViewFactory() {

    }

    public static MainView create(
            MainViewModel mainViewModel,
            PortfoliosController portfoliosController,
            NewsController newsController,
            NavigationController navigationController,
            SearchController searchController,
            SearchViewModel searchViewModel
    ) {
        return new MainView(
                mainViewModel,
                portfoliosController,
                newsController,
                navigationController,
                searchController,
                searchViewModel
        );
    }
}
