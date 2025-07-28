package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.analysis.AnalysisController;
import interface_adapter.analysis.AnalysisInteractor;
import interface_adapter.analysis.AnalysisPresenter;
import interface_adapter.analysis.AnalysisViewModel;
import use_case.analysis.AnalysisInputBoundary;
import use_case.analysis.AnalysisOutputBoundary;
import use_case.analysis.AnalysisStockDataAccessInterface;
import use_case.analysis.AnalysisTransactionDataAccessInterface;

public class AnalysisUseCaseFactory {
    private AnalysisUseCaseFactory() {

    }

    public static AnalysisController create(
            ViewManagerModel viewManagerModel,
            AnalysisViewModel analysisViewModel,
            AnalysisStockDataAccessInterface stockDataAccessObject,
            AnalysisTransactionDataAccessInterface transactionDataAccessObject
    ) {
        AnalysisOutputBoundary analysisPresenter = new AnalysisPresenter(
                viewManagerModel,
                analysisViewModel
        );
        AnalysisInputBoundary analysisInteractor = new AnalysisInteractor(stockDataAccessObject,
                transactionDataAccessObject, analysisPresenter);
        return new AnalysisController(analysisInteractor);
    }
}
