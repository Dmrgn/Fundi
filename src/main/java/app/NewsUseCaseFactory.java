package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.news.NewsController;
import interface_adapter.news.NewsPresenter;
import interface_adapter.news.NewsViewModel;
import use_case.news.NewsInputBoundary;
import use_case.news.NewsInteractor;
import use_case.news.NewsOutputBoundary;
import use_case.portfolio.PortfolioTransactionDataAccessInterface;

public class NewsUseCaseFactory {
    private NewsUseCaseFactory() {

    }

    public static NewsController create(
            ViewManagerModel viewManagerModel,
            NewsViewModel newsViewModel,
            PortfolioTransactionDataAccessInterface transactionDataAccessInterface
    ) {
        NewsOutputBoundary newsPresenter = new NewsPresenter(viewManagerModel, newsViewModel);
        NewsInputBoundary newsInteractor = new NewsInteractor(newsPresenter, transactionDataAccessInterface);
        return new NewsController(newsInteractor);
    }

}
