package usecase.analysis;

/**
 * The Analysis Use Case Input Data.
 */
public class AnalysisInputData {
    private final String portfolioId;

    public AnalysisInputData(String portfolioId) {
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
