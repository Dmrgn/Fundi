package use_case.portfolioHub;

import java.util.Map;

/**
 * Output Data for the Portfolios Use Case.
 */
public class PortfolioHubOutputData {

    private final String username;
    private final Map<String, String> portfolios;

    public PortfolioHubOutputData(String username, Map<String, String> portfolios) {
        this.username = username;
        this.portfolios = portfolios;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, String> getPortfolios() {
        return portfolios;
    }

}