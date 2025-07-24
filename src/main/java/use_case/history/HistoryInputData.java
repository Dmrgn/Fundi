package use_case.history;

public class HistoryInputData {
    String portfolioId;

    public HistoryInputData(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }
}
