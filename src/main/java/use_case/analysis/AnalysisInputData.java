package use_case.analysis;

public class AnalysisInputData {
    String portfolioId;

    public AnalysisInputData(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }
}
