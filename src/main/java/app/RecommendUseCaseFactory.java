package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.recommend.RecommendController;
import use_case.recommend.RecommendInteractor;
import interface_adapter.recommend.RecommendPresenter;
import interface_adapter.recommend.RecommendViewModel;
import use_case.recommend.RecommendDataAccessInterface;
import use_case.recommend.RecommendInputBoundary;
import use_case.recommend.RecommendOutputBoundary;

public class RecommendUseCaseFactory {
    private RecommendUseCaseFactory() {

    }

    public static RecommendController create(
            ViewManagerModel viewManagerModel,
            RecommendViewModel recommendViewModel,
            RecommendDataAccessInterface dataAccessObject
    ) {
        RecommendOutputBoundary recommendPresenter = new RecommendPresenter(
                recommendViewModel,
                viewManagerModel
        );
        RecommendInputBoundary recommendInteractor = new RecommendInteractor(
                dataAccessObject,
                recommendPresenter
        );
        return new RecommendController(recommendInteractor);
    }
}
