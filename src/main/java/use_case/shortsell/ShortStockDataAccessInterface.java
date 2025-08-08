package use_case.shortsell;

public interface ShortStockDataAccessInterface {
    double getPrice(String ticker);
    boolean hasTicker(String ticker);
}
