package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.analysis.AnalysisController;
import interfaceadapter.analysis.AnalysisPresenter;
import interfaceadapter.analysis.AnalysisViewModel;
import interfaceadapter.navigation.NavigationController;
import usecase.analysis.AnalysisInputBoundary;
import usecase.analysis.AnalysisInteractor;
import usecase.analysis.AnalysisOutputBoundary;
import usecase.analysis.AnalysisStockDataAccessInterface;
import usecase.analysis.AnalysisTransactionDataAccessInterface;

/**
 * Factory for the Analysis Use case.
 */
public final class AnalysisUseCaseFactory {
    private AnalysisUseCaseFactory() {

    }

    /**
     * Create the Analysis Controller.
     * @param viewManagerModel The View Manager Model
     * @param analysisViewModel The Analysis View Model
     * @param stockDataAccessObject The Stock DAO
     * @param transactionDataAccessObject The Transaction DAO
     * @param navigationController The Navigation Controller
     * @return The Analysis Controller
     */
    public static AnalysisController create(
            ViewManagerModel viewManagerModel,
            AnalysisViewModel analysisViewModel,
            AnalysisStockDataAccessInterface stockDataAccessObject,
            AnalysisTransactionDataAccessInterface transactionDataAccessObject,
            NavigationController navigationController
    ) {
        AnalysisOutputBoundary analysisPresenter = new AnalysisPresenter(
                viewManagerModel,
                analysisViewModel,
                navigationController
        );
        AnalysisInputBoundary analysisInteractor = new AnalysisInteractor(stockDataAccessObject,
                transactionDataAccessObject, analysisPresenter);
        return new AnalysisController(analysisInteractor);
    }
}
