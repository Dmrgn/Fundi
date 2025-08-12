package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.recommend.RecommendController;
import interfaceadapter.recommend.RecommendPresenter;
import interfaceadapter.recommend.RecommendViewModel;
import usecase.recommend.RecommendInputBoundary;
import usecase.recommend.RecommendInteractor;
import usecase.recommend.RecommendOutputBoundary;
import usecase.recommend.RecommendStockDataAccessInterface;
import usecase.recommend.RecommendTransactionDataAccessInterface;

/**
 * Factory for the Recommend Use Case.
 */
public final class RecommendUseCaseFactory {
    private RecommendUseCaseFactory() {

    }

    /**
     * Create the Recommend Controller.
     * @param viewManagerModel The View Manager Model
     * @param recommendViewModel The Recommend View Model
     * @param stockDataAccessObject The Stock DAO
     * @param transactionDataAccessObject The Transaction DAO
     * @return The Recommend Controller
     */
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
