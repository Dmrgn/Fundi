package app;

import interface_adapter.news.NewsViewModel;
import interface_adapter.navigation.NavigationController;
import view.NewsView;

/**
 * Factory for the News View
 */
public class NewsViewFactory {
    private NewsViewFactory() {
    
    }

    public static NewsView create(NewsViewModel newsViewModel, NavigationController navigationController) {
        return new NewsView(newsViewModel, navigationController);
    }
}
