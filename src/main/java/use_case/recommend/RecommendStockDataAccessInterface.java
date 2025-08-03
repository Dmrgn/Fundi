package use_case.recommend;


import entity.StockData;

import java.util.List;
import java.util.Set;

/**
 * The Stock DAO for the Recommend Use Case
 */
public interface RecommendStockDataAccessInterface {
    /**
     * Get the past stock data for a given ticker
     * @param ticker The name of the ticker
     * @return The past stock data for a given ticker
     */
    List<StockData> pastStockData(String ticker);

    /**
     * Get the available tickers in the DAO
     * @return A set of the available tickers
     */
    Set<String> getAvailableTickers();
}
