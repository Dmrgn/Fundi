package usecase.delete_portfolio;

public class DeletePortfolioInputData {
    private final String username;
    private final String portfolioName;

    public DeletePortfolioInputData(String username, String portfolioName) {
        this.username = username;
        this.portfolioName = portfolioName;
    }

    public String getUsername() {
        return username;
    }

    public String getPortfolioName() {
        return portfolioName;
    }
}
