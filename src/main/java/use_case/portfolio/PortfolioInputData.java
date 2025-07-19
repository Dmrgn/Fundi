package use_case.portfolio;

/**
 * The Input Data for the Portfolio Use Case.
 */
public class PortfolioInputData {

    private final String username;
    private final String portfolioId;

    public PortfolioInputData(String username, String portfolioId) {
        this.username = username;
        this.portfolioId = portfolioId;
    }

    public String getUsername() {
        return username;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

}
