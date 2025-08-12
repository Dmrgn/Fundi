package usecase.buy;

/**
 * Output data for the Buy Use Case.
 */
public class BuyOutputData {
    private final String ticker;
    private final double price;
    private final int quantity;

    public BuyOutputData(String ticker, double price, int quantity) {
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
