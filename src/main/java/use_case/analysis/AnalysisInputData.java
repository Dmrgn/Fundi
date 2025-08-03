package use_case.analysis;

/**
 * The Analysis Use Case Input Data
 */
public class AnalysisInputData {
    String portfolioId;

    public AnalysisInputData(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }
}
