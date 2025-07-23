package use_case.recommend;


import java.util.List;
import java.util.Map;

public interface RecommendDataAccessInterface {
    Map<String, List<Double>> pastStockData();
}
