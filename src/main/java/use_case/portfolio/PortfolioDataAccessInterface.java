package use_case.portfolio;

import entity.Transaction;

import java.util.List;

/**
 * DAO for the Portfolio Use Case.
 */
public interface PortfolioDataAccessInterface {

    /**
     * Get the past transactions data
     * @param portfolioId the id to search at
     * @return A list of transactions
     */
    List<Transaction> pastTransactions(String portfolioId);
}