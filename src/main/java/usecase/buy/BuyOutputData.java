package usecase.buy;

/**
 * Output data for the Buy Use Case.
 */
public class BuyOutputData {
    private final String ticker;
    private final double price;
    private final int quantity;
    private final double balance;
    private final boolean success;
    private final String message;

    public BuyOutputData(String ticker, double price, int quantity, double balance, boolean success, String message) {
        this.ticker = ticker;
        this.price = price;
        this.quantity = quantity;
        this.balance = balance;
        this.success = success;
        this.message = message;
    }

    /**
     * Getter.
     * 
     * @return Ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Getter.
     * 
     * @return Price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter.
     * 
     * @return Quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Getter.
     * 
     * @return Balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Getter.
     * 
     * @return Success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Getter.
     * 
     * @return Message
     */
    public String getMessage() {
        return message;
    }
}
