package use_case.sell;

public class SellOutputData {
    String ticker;
    double price;
    int quantity;

    public SellOutputData(String ticker, double price, int quantity) {
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
