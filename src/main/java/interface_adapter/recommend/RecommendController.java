package interface_adapter.recommend;

import use_case.recommend.RecommendInputBoundary;
import use_case.recommend.RecommendInputData;

/**
 * The controller for the Recommend Use Case
 */
public class RecommendController {
    RecommendInputBoundary recommendInputBoundary;

    public RecommendController(RecommendInputBoundary recommendInputBoundary) {
        this.recommendInputBoundary = recommendInputBoundary;
    }

    /**
     * Executes the Recommend Use Case
     * @param portfolioId The portfolio Id to get recommendations for
     */
    public void execute(String portfolioId) {
        recommendInputBoundary.execute(new RecommendInputData(portfolioId));
    }

    /**
     * Route to the Portfolio View
     */
    public void routeToPortfolio() {
        recommendInputBoundary.routeToPortfolio();
    }
}
