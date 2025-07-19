package use_case.portfolio;

import entity.User;

/**
 * DAO for the Login Use Case.
 */
public interface PortfolioUserDataAccessInterface {

    /**
     * Get the portfolio data
     * @param portfolioId the id to search at
     */
    void getPortfolio(String portfolioId);
}