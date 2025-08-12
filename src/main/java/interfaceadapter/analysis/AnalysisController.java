package interfaceadapter.analysis;

import usecase.analysis.AnalysisInputBoundary;
import usecase.analysis.AnalysisInputData;

/**
 * The controller for the Analysis Use Case.
 */
public class AnalysisController {
    private final AnalysisInputBoundary analysisInputBoundary;

    public AnalysisController(AnalysisInputBoundary analysisInputBoundary) {
        this.analysisInputBoundary = analysisInputBoundary;
    }

    /**
     * Executes the Analysis Use Case.
     * @param portfolioId The id of the portfolio to analyze
     */
    public void execute(String portfolioId) {
        analysisInputBoundary.execute(new AnalysisInputData(portfolioId));
    }

    /**
     * Route to the Portfolio View.
     */
    public void routeToPortfolio() {
        analysisInputBoundary.routeToPortfolio();
    }
}
