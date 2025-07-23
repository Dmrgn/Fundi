package use_case.delete;

public class DeleteInputData {
    String portfolioId;

    public DeleteInputData(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }
}
