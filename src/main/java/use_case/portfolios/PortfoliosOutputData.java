package use_case.portfolios;

/**
 * Output Data for the Portfolios Use Case.
 */
public class PortfoliosOutputData {

    private final String username;
    private final String portfolioName;

    public PortfoliosOutputData(String username, String portfolioName) {
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