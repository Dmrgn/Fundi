package use_case.recommend;

public class RecommendInputData {
    private final String portfolioId;

    public RecommendInputData(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }
}
