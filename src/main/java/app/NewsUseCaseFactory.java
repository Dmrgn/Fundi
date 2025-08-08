package app;

import data_access.FinnhubSearchDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.news.NewsController;
import interface_adapter.news.NewsPresenter;
import interface_adapter.news.NewsViewModel;
import use_case.news.NewsInputBoundary;
import use_case.news.NewsInteractor;
import use_case.news.NewsOutputBoundary;
import use_case.portfolio.PortfolioTransactionDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.IOException;

/**
 * Factory for the News Use Case
 */
public class NewsUseCaseFactory {
    private NewsUseCaseFactory() {

    }

    public static NewsController create(
            ViewManagerModel viewManagerModel,
            NewsViewModel newsViewModel,
            PortfolioTransactionDataAccessInterface transactionDataAccessInterface,
            SearchDataAccessInterface searchDataAccess
    ) {
        NewsOutputBoundary newsPresenter = new NewsPresenter(viewManagerModel, newsViewModel);
        NewsInputBoundary newsInteractor;
        try {
            // Pass the search DAO to the interactor
            newsInteractor = new NewsInteractor(newsPresenter, transactionDataAccessInterface, searchDataAccess);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize NewsInteractor: " + e.getMessage(), e);
        }
        return new NewsController(newsInteractor);
    }

}
