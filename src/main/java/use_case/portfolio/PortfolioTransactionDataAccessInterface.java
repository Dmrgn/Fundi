package use_case.portfolio;

import java.util.List;

import entity.Transaction;

/**
 * DAO for the Portfolio Use Case.
 */
public interface PortfolioTransactionDataAccessInterface {

    /**
     * Get the past transactions data.
     * @param portfolioId the id to search at
     * @return A list of transactions
     */
    List<Transaction> pastTransactions(String portfolioId);

    /**
     * Gets a list of unique stock symbols for a given user.
     * @param username The user's username.
     * @return A list of stock symbols (e.g., ["AAPL", "GOOGL"]).
     */
    List<String> getUserSymbols(String username);
}