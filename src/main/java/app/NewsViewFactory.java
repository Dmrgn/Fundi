package app;

import interface_adapter.news.NewsViewModel;
import view.NewsView;

public class NewsViewFactory {
    private NewsViewFactory() {

    }

    public static NewsView create(NewsViewModel newsViewModel) {
        return new NewsView(newsViewModel);
    }
}
