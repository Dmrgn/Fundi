package usecase.portfolio_hub;

import java.util.Map;

/**
 * DAO for the Portfolio Hub Use Case.
 */
public interface PortfolioHubDataAccessInterface {

    /**
     * Get the portfolio data.
     * @param username the name to search at
     * @return A map from id to portfolio name
     */
    Map<String, String> getPortfolios(String username);

    /**
     * Save portfolio data.
     * @param portfolioName the Name
     * @param username the username
     */
    void save(String portfolioName, String username);
}
