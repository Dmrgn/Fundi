package usecase.sell;

/**
 * Output data for the Sell Use Case.
 */
public class SellOutputData {
    private final String ticker;
    private final double price;
    private final int quantity;

    public SellOutputData(String ticker, double price, int quantity) {
        this.ticker = ticker;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Getter.
     * @return Ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Getter.
     * @return Price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter.
     * @return Quantity
     */
    public int getQuantity() {
        return quantity;
    }
}
