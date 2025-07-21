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
     * @param username the username
     */
    void save(String portfolioName, String username);

    /**
     * Returns the id of the given username
     * @param username The username
     * @return The id of the username
     */
    String getId(String username);
}