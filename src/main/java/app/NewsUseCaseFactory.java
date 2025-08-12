package app;

import dataaccess.FinnhubNewsDataAccessObject;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.news.NewsController;
import interfaceadapter.news.NewsPresenter;
import interfaceadapter.news.NewsViewModel;
import usecase.news.NewsAPIDataAccessInterface;
import usecase.news.NewsInputBoundary;
import usecase.news.NewsInteractor;
import usecase.news.NewsOutputBoundary;
import usecase.portfolio.PortfolioTransactionDataAccessInterface;
import usecase.search.SearchDataAccessInterface;

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
            SearchDataAccessInterface searchDataAccess // Added
    ) {
        NewsOutputBoundary newsPresenter = new NewsPresenter(viewManagerModel, newsViewModel);
        NewsInputBoundary newsInteractor;
        try {
            NewsAPIDataAccessInterface newsAPIDataAccess = new FinnhubNewsDataAccessObject();
            // Pass the search DAO to the interactor
            newsInteractor = new NewsInteractor(newsPresenter, transactionDataAccessInterface, searchDataAccess, newsAPIDataAccess);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize NewsInteractor: " + e.getMessage(), e);
        }
        return new NewsController(newsInteractor);
    }

}
