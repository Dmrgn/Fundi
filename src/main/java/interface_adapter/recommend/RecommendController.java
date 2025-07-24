package interface_adapter.recommend;

import use_case.recommend.RecommendInputBoundary;
import use_case.recommend.RecommendInputData;

public class RecommendController {
    RecommendInputBoundary recommendInputBoundary;

    public RecommendController(RecommendInputBoundary recommendInputBoundary) {
        this.recommendInputBoundary = recommendInputBoundary;
    }

    public void execute() {
        recommendInputBoundary.execute(new RecommendInputData());
    }

    public void routeToPortfolio() {
        recommendInputBoundary.routeToPortfolio();
    }
}
