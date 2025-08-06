package use_case.analysis;

import java.util.List;

import entity.StockData;

/**
 * The Stock DAO for the Analysis Use Case.
 */
public interface AnalysisStockDataAccessInterface {
    /**
     * Get the past stock data for a given ticker.
     * @param ticker The name of the ticker
     * @return The past stock data for a given ticker
     */
    List<StockData> pastStockData(String ticker);
}
