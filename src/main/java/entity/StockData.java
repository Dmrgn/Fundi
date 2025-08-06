package entity;

import java.time.LocalDate;

/**
 * An entity representing data for a given stock.
 */
public class StockData {
    private final String ticker;
    private final LocalDate timestamp;
    private final double price;

    public StockData(String ticker, LocalDate timestamp, double price) {
        this.ticker = ticker;
        this.timestamp = timestamp;
        this.price = price;
    }

    /**
     * Getter.
     * @return ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Getter.
     * @return Timestamp
     */
    public LocalDate getTimestamp() {
        return timestamp;
    }

    /**
     * Getter.
     * @return Price
     */
    public double getPrice() {
        return price;
    }
}
