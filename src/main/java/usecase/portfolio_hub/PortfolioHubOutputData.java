package usecase.portfolio_hub;

import java.util.Map;

/**
 * Output Data for the Portfolio Hub Use Case.
 */
public class PortfolioHubOutputData {

    private final String username;
    private final Map<String, String> portfolios;

    public PortfolioHubOutputData(String username, Map<String, String> portfolios) {
        this.username = username;
        this.portfolios = portfolios;
    }

    /**
     * Getter.
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter.
     * @return Portfolios
     */
    public Map<String, String> getPortfolios() {
        return portfolios;
    }

}
