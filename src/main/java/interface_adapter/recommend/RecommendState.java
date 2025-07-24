package interface_adapter.recommend;

public class RecommendState {
    String[] recommendations;
    double[] prices;

    public RecommendState(RecommendState copy) {
        this.recommendations = copy.recommendations;
        this.prices = copy.prices;
    }

    public RecommendState() {

    }

    public String[] getRecommendations() {
        return recommendations.clone();
    }

    public void setRecommendations(String[] recommendations) {
        this.recommendations = recommendations.clone();
    }

    public double[] getPrices() {
        return prices.clone();
    }

    public void setPrices(double[] prices) {
        this.prices = prices.clone();
    }
}
