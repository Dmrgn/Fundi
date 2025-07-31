package interface_adapter.news;

import use_case.news.NewsInputBoundary;
import use_case.news.NewsInputData;

public class NewsController {
    private final NewsInputBoundary newsUseCaseInteractor;

    public NewsController(NewsInputBoundary newsUseCaseInteractor) {
        this.newsUseCaseInteractor = newsUseCaseInteractor;
    }

    public void execute(String username) {
        NewsInputData inputData = new NewsInputData(username);
        newsUseCaseInteractor.execute(inputData);
    }

    public void executeSearch(String searchQuery) {
        NewsInputData inputData = new NewsInputData(searchQuery);
        newsUseCaseInteractor.execute(inputData);
    }
}