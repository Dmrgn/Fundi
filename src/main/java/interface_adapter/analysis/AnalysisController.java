package interface_adapter.analysis;

import use_case.analysis.AnalysisInputBoundary;
import use_case.analysis.AnalysisInputData;

public class AnalysisController {
    private final AnalysisInputBoundary analysisInputBoundary;

    public AnalysisController(AnalysisInputBoundary analysisInputBoundary) {
        this.analysisInputBoundary = analysisInputBoundary;
    }

    public void execute(String portfolioId) {
        analysisInputBoundary.execute(new AnalysisInputData(portfolioId));
    }

    public void routeToPortfolio() {
        analysisInputBoundary.routeToPortfolio();
    }
}
