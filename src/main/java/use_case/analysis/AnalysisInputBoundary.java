package use_case.analysis;

/**
 * Input Boundary for the Analysis Use Case.
 */
public interface AnalysisInputBoundary {
    /**
     * Execute the Analysis Use Case.
     * @param analysisInputData The input data
     */
    void execute(AnalysisInputData analysisInputData);

    /**
     * Switch to the Portfolio View Model.
     */
    void routeToPortfolio();
}
