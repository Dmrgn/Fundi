package use_case.recommend;


import entity.StockData;

import java.util.List;

public interface RecommendDataAccessInterface {
    List<StockData> pastStockData(String ticker);
    String[] getAvailableTickers();
}
