package use_case.buy;

public interface BuyStockDataAccessInterface {
    double getPrice(String ticker);
    boolean hasTicker(String ticker);
}
