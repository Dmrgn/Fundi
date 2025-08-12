package usecase.dashboard;

import entity.Transaction;
import java.util.List;
import java.util.Map;

/**
 * Data access interface for the Dashboard use case.
 */
public interface DashboardDataAccessInterface {
    /**
     * Get all portfolios for a user.
     * 
     * @param username the username
     * @return map of portfolio name to portfolio ID
     */
    Map<String, String> getPortfolios(String username);

    /**
     * Get all transactions for a portfolio.
     * 
     * @param portfolioId the portfolio ID
     * @return list of transactions
     */
    List<Transaction> getTransactions(String portfolioId);

    /**
     * Get current stock price for a ticker.
     * 
     * @param ticker the stock ticker
     * @return current price
     */
    double getCurrentStockPrice(String ticker);
}
