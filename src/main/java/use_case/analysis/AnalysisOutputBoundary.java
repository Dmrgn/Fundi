package use_case.analysis;

/**
 * The Output Boundary for the Analysis Use Case.
 */
public interface AnalysisOutputBoundary {
    /**
     * Prepare the view.
     * @param analysisOutputData The output data
     */
    void prepareView(AnalysisOutputData analysisOutputData);

    /**
     * Switch to the Portfolio View.
     */
    void routeToPortfolio();
}
