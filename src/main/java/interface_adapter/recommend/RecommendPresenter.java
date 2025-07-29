package interface_adapter.recommend;

import interface_adapter.ViewManagerModel;
import use_case.recommend.RecommendOutputBoundary;
import use_case.recommend.RecommendOutputData;

public class RecommendPresenter implements RecommendOutputBoundary {
    RecommendViewModel recommendViewModel;
    ViewManagerModel viewManagerModel;

    public RecommendPresenter(RecommendViewModel recommendViewModel, ViewManagerModel viewManagerModel) {
        this.recommendViewModel = recommendViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareView(RecommendOutputData recommendOutputData) {
        RecommendState recommendState = recommendViewModel.getState();
        recommendState.setRecommendations(recommendOutputData.getRecommendations());
        recommendState.setPrices(recommendOutputData.getPrices());
        recommendViewModel.setState(recommendState);
        recommendViewModel.firePropertyChanged();
        viewManagerModel.setState(recommendViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void routeToPortfolio() {
        viewManagerModel.setState("portfolio");
        viewManagerModel.firePropertyChanged();
    }
}
