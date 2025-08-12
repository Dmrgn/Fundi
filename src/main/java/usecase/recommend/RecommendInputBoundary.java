package usecase.recommend;

/**
 * Input Boundary for the Recommend Use Case.
 */
public interface RecommendInputBoundary {

    /**
     * Execute the Recommend Use Case.
     * @param recommendInputData the input data
     */
    void execute(RecommendInputData recommendInputData);

    /**
     * Switch to the Portfolio View.
     */
    void routeToPortfolio();
}
