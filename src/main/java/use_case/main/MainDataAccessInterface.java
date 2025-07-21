package use_case.main;

import java.util.Map;

/**
 * DAO for the main use ase.
 */
public interface MainDataAccessInterface {

    /**
     * Get the portfolio data
     * @param username the name to search at
     * @return A list of portfolio names
     */
    Map<String, String> getPortfolios(String username);
}