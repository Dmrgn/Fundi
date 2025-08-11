package use_case.sell;

import entity.Transaction;

/**
 * The Transaction DAO for the Sell Use Case.
 */
public interface SellTransactionDataAccessInterface {
    /**
     * Get the amount of a ticker in the given portfolio.
     * 
     * @param portfolioId The portfolio id
     * @param ticker      The name of the ticker
     * @return The amount of the ticker in the portfolio
     */
    int amountOfTicker(String portfolioId, String ticker);

    /**
     * Get the current holdings of a ticker in the given portfolio.
     * 
     * @param portfolioId The portfolio id
     * @param ticker      The name of the ticker
     * @return The current holdings of the ticker in the portfolio
     *
     *         Convention: buys have positive price, sells have negative price;
     *         holdings are computed as sum(amount) where price > 0 and subtract
     *         sum(amount) where price < 0.
     */
    int getCurrentHoldings(String portfolioId, String ticker);

    /**
     * Save the transaction in the DAO.
     * 
     * @param transaction The new transaction
     */
    void save(Transaction transaction);

}
