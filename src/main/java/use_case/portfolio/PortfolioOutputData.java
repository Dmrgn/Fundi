package use_case.portfolio;

/**
 * Output Data for the Portfolio Use Case.
 */
public class PortfolioOutputData {

    private final String username;
    private final String portfolioId;

    public PortfolioOutputData(String username, String portfolioId) {
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