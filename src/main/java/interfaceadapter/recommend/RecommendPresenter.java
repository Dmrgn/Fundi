package interfaceadapter.recommend;

import interfaceadapter.ViewManagerModel;
import usecase.recommend.RecommendOutputBoundary;
import usecase.recommend.RecommendOutputData;

/**
 * The Presenter for the Recommend Use Case.
 */
public class RecommendPresenter implements RecommendOutputBoundary {
    private final RecommendViewModel recommendViewModel;
    private final ViewManagerModel viewManagerModel;

    public RecommendPresenter(RecommendViewModel recommendViewModel, ViewManagerModel viewManagerModel) {
        this.recommendViewModel = recommendViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepare the view.
     * @param recommendOutputData The output data
     */
    @Override
    public void prepareView(RecommendOutputData recommendOutputData) {
        RecommendState recommendState = recommendViewModel.getState();
        recommendState.setHaveRecs(recommendOutputData.getHaveRecs());
        recommendState.setNotHaveRecs(recommendOutputData.getNotHaveRecs());
        recommendState.setSafeRecs(recommendOutputData.getSafeRecs());
        recommendViewModel.setState(recommendState);
        recommendViewModel.firePropertyChanged();
        viewManagerModel.setState(recommendViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Switch to the Portfolio View.
     */
    @Override
    public void routeToPortfolio() {
        viewManagerModel.setState("portfolio");
        viewManagerModel.firePropertyChanged();
    }
}
