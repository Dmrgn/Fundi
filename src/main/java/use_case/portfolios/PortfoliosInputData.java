package use_case.portfolios;

/**
 * The Input Data for the Portfolios Use Case.
 */
public class PortfoliosInputData {

    private final String username;
    private final String portfolioName;

    public PortfoliosInputData(String username, String portfolioName) {
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
