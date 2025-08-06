package use_case.recommend;

/**
 * The Output Boundary for the Recommend Use Case.
 */
public interface RecommendOutputBoundary {

    /**
     * Prepare the view.
     * @param recommendOutputData The output data
     */
    void prepareView(RecommendOutputData recommendOutputData);

    /**
     * Switch to the Portfolio View.
     */
    void routeToPortfolio();
}
