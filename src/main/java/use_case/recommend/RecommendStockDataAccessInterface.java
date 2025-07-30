package use_case.recommend;


import entity.StockData;

import java.util.List;
import java.util.Set;

public interface RecommendStockDataAccessInterface {
    List<StockData> pastStockData(String ticker);
    List<String> getAvailableTickers();
}
