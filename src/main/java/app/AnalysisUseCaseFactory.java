package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.analysis.AnalysisController;
import use_case.analysis.AnalysisInteractor;
import interface_adapter.analysis.AnalysisPresenter;
import interface_adapter.analysis.AnalysisViewModel;
import interface_adapter.navigation.NavigationController;

import use_case.analysis.AnalysisInputBoundary;
import use_case.analysis.AnalysisOutputBoundary;
import use_case.analysis.AnalysisStockDataAccessInterface;
import use_case.analysis.AnalysisTransactionDataAccessInterface;

/**
 * Factory for the Analysis Use case
 */
public class AnalysisUseCaseFactory {
    private AnalysisUseCaseFactory() {

    }

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
