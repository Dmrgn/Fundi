package use_case.recommend;

public interface RecommendOutputBoundary {
    void prepareView(RecommendOutputData recommendOutputData);
    void routeToPortfolio();
}
