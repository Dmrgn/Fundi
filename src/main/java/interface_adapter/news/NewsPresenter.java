package interface_adapter.news;

import interface_adapter.ViewManagerModel;
import use_case.news.NewsOutputBoundary;
import use_case.news.NewsOutputData;

public class NewsPresenter implements NewsOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final NewsViewModel newsViewModel;

    public NewsPresenter(ViewManagerModel viewManagerModel, NewsViewModel newsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.newsViewModel = newsViewModel;
    }

    @Override
    public void prepareView(NewsOutputData newsOutputData) {
        // Update the view model with new data
        NewsState newsState = newsViewModel.getState();
        newsState.setNewsItems(newsOutputData.getNewsItems());
        newsViewModel.setState(newsState);
        newsViewModel.firePropertyChanged();

        // Switch to news view
        viewManagerModel.setState(newsViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}