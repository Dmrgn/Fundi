package use_case.sell;

public interface SellStockDataAccessInterface {
    double getPrice(String ticker);
    boolean hasTicker(String ticker);
}
