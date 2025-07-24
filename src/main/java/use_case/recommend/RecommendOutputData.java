package use_case.recommend;

public class RecommendOutputData {
    String[] recommendations;
    double[] prices;

    public RecommendOutputData(String[] recommendations, double[] prices) {
        this.recommendations = recommendations;
        this.prices = prices;
    }

    public String[] getRecommendations() {
        return recommendations;
    }

    public double[] getPrices() {
        return prices;
    }
}
