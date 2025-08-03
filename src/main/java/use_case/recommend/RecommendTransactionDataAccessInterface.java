package use_case.recommend;

import java.util.Set;

/**
 * The Transaction DAO for the Recommend Use Case
 */
public interface RecommendTransactionDataAccessInterface {
    /**
     * Get the tickers in a given portfolio
     * @param portfolioId The id of the portfolio
     * @return The set of tickers in the portfolio
     */
    public Set<String> getPortfolioTickers(String portfolioId);
}
