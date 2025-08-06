package use_case.recommend;

/**
 * Input data for the Recommend Use Case.
 */
public class RecommendInputData {
    private final String portfolioId;

    public RecommendInputData(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    /**
     * Getter.
     * @return Portfolio Id
     */
    public String getPortfolioId() {
        return portfolioId;
    }
}
