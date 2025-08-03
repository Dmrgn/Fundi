package use_case.create;

import use_case.portfolioHub.PortfolioHubDataAccessInterface;

/**
 * DAO for the Create Use Case.
 */
public interface CreateDataAccessInterface extends PortfolioHubDataAccessInterface {

    /**
     * Checks if the given portfolio exists for the user.
     * @param portfolioName the portfolioName to look for
     * @param username the users name
     * @return true if a portfolio with this name exists; false otherwise
     */
    boolean existsByName(String portfolioName, String username);
}