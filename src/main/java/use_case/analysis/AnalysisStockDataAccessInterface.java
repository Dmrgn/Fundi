package use_case.analysis;

import entity.StockData;

import java.util.List;

/**
 * The Stock DAO for the Analysis Use Case
 */
public interface AnalysisStockDataAccessInterface {
    /**
     * Get the past stock data for a given ticker
     * @param ticker The name of the ticker
     * @return The past stock data for a given ticker
     */
    List<StockData> pastStockData(String ticker);
}
