package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendPresenter;
import interface_adapter.recommend.RecommendViewModel;
import use_case.recommend.RecommendInputBoundary;
import use_case.recommend.RecommendInteractor;
import use_case.recommend.RecommendOutputBoundary;
import use_case.recommend.RecommendStockDataAccessInterface;
import use_case.recommend.RecommendTransactionDataAccessInterface;

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
