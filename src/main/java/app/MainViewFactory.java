package app;

import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolios.PortfoliosController;
import view.MainView;

public class MainViewFactory {
    private MainViewFactory() {

    }

    public static MainView create(
            MainViewModel mainViewModel,
            PortfoliosController portfoliosController,
            NewsController newsController
    ) {
        return new MainView(
                mainViewModel,
                portfoliosController,
                newsController
        );
    }
}
