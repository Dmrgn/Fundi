package use_case.buy;

public class BuyOutputData {
    String ticker;
    double price;
    int quantity;

    public BuyOutputData(String ticker, double price, int quantity) {
        this.ticker = ticker;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
