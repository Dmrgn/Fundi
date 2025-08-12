package usecase.history;

import java.util.List;

import entity.Transaction;

/**
 * The DAO for the History Use Case.
 */
public interface HistoryDataAccessInterface {
    /**
     * Get the past transactions for the given portfolio.
     * @param portfolioId The id of the portfolio
     * @return A list of transactions for the portfolio
     */
    List<Transaction> pastTransactions(String portfolioId);
}
