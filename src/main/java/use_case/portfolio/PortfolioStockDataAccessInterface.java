package use_case.portfolio;

/**
 * The Stock DAO for the Portfolio Use Case.
 */
public interface PortfolioStockDataAccessInterface {

    /**
     * Return the current price of a given ticker.
     * @param ticker The ticker to get the price of
     * @return The price of the ticker on the most recent trading day
     */
    double getPrice(String ticker);
}
