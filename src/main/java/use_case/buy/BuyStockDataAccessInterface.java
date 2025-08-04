package use_case.buy;

/**
 * The Stock DAO for the Buy Use Case.
 */
public interface BuyStockDataAccessInterface {
    /**
     * Get the most recent price of a given ticker.
     * @param ticker The ticker
     * @return The current price of the ticker
     */
    double getPrice(String ticker);

    /**
     * Check if the DAO supports the given ticker.
     * @param ticker The ticker
     * @return Whether the DAO has the ticker
     */
    boolean hasTicker(String ticker);
}
