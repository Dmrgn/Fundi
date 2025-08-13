package usecase.buy;

import entity.Transaction;

/**
 * The Transaction DAO for the Buy Use Case.
 */
public interface BuyTransactionDataAccessInterface {
    /**
     * Save the transaction in the DAO.
     * @param transaction The new transaction
     */
    void save(Transaction transaction);

    /**
     * Get the current balance of a portfolio.
     * @param portfolioId The portfolio ID
     * @return The current balance
     */
    double getPortfolioBalance(String portfolioId);

    /**
     * Update the balance of a portfolio.
     * @param portfolioId The portfolio ID
     * @param newBalance The new balance
     */
    void updatePortfolioBalance(String portfolioId, double newBalance);
}
