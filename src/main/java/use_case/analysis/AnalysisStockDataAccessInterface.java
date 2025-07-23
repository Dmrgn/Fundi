package use_case.analysis;

import entity.StockData;

import java.util.List;

public interface AnalysisStockDataAccessInterface {
    List<StockData> getPastPrices(String ticker);
}
