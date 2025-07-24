package use_case.portfolio;

/**
 * The Input Data for the Portfolio Use Case.
 */
public class PortfolioInputData {

    private final String username;
    private final String portfolioId;
    private final String portfolioName;

    public PortfolioInputData(String username, String portfolioId, String portfolioName) {
        this.username = username;
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public String getUsername() {
        return username;
    }
}
