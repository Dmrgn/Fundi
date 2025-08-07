package interface_adapter.news;

import use_case.news.NewsInputBoundary;
import use_case.news.NewsInputData;

public class NewsController {
    private final NewsInputBoundary newsUseCaseInteractor;

    public NewsController(NewsInputBoundary newsUseCaseInteractor) {
        this.newsUseCaseInteractor = newsUseCaseInteractor;
    }

    /**
     * Executes a request for portfolio-based news for the given username.
     * @param username The user's name.
     */
    public void execute(String username) {
        NewsInputData inputData = new NewsInputData(username, false);
        newsUseCaseInteractor.execute(inputData);
    }

    /**
     * Executes a search for news based on a query.
     * @param searchQuery The search term (e.g., a stock symbol).
     */
    public void executeSearch(String searchQuery) {
        NewsInputData inputData = new NewsInputData(searchQuery, true);
        newsUseCaseInteractor.execute(inputData);
    }
}