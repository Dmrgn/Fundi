package use_case.main;

import java.util.Map;

/**
 * DAO for the main use ase.
 */
public interface MainDataAccessInterface {

    /**
     * Get the portfolio data
     * @param username the name to search at
     * @return A map from id to portfolio name
     */
    Map<String, String> getPortfolios(String username);

    /**
     * Save portfolio data
     * @param portfolioName the Name
     * @param portfolioId the id
     */
    void save(String portfolioName, String username);
}