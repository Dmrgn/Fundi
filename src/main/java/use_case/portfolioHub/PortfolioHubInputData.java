package use_case.portfolioHub;

/**
 * The Input Data for the Portfolios Use Case.
 */
public class PortfolioHubInputData {

    private final String username;

    public PortfolioHubInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
