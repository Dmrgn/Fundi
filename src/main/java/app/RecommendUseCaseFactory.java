package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.recommend.RecommendController;
import use_case.recommend.*;
import interface_adapter.recommend.RecommendPresenter;
import interface_adapter.recommend.RecommendViewModel;
import use_case.recommend.RecommendStockDataAccessInterface;

/**
 * Factory for the Recommend Use Case
 */
public class RecommendUseCaseFactory {
    private RecommendUseCaseFactory() {

    }

    public static RecommendController create(
            ViewManagerModel viewManagerModel,
            RecommendViewModel recommendViewModel,
            RecommendStockDataAccessInterface stockDataAccessObject,
            RecommendTransactionDataAccessInterface transactionDataAccessObject
    ) {
        RecommendOutputBoundary recommendPresenter = new RecommendPresenter(
                recommendViewModel,
                viewManagerModel
        );
        RecommendInputBoundary recommendInteractor = new RecommendInteractor(
                stockDataAccessObject,
                transactionDataAccessObject,
                recommendPresenter
        );
        return new RecommendController(recommendInteractor);
    }
}
