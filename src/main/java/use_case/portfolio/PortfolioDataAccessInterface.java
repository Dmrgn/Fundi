package use_case.portfolio;

import entity.Portfolio;

/**
 * DAO for the Portfolio Use Case.
 */
public interface PortfolioDataAccessInterface {

    /**
     * Get the portfolio data
     * @param portfolioId the id to search at
     * @return A portfolio object
     */
    Portfolio getPortfolio(String portfolioId);
}