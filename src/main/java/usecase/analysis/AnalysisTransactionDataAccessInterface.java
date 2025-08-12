package usecase.analysis;

import java.util.List;

import entity.Transaction;

/**
 * The Transaction DAO for the Analysis Use Case.
 */
public interface AnalysisTransactionDataAccessInterface {
    /**
     * Get the past transactions for the given portfolio.
     * @param portfolioId The id of the portfolio
     * @return A list of transactions for the portfolio
     */
    List<Transaction> pastTransactions(String portfolioId);

}
