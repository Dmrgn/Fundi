package entity;

import java.time.LocalDate;

/**
 * An entity representing data for a given stock
 */
public class StockData {
    String ticker;
    LocalDate timestamp;
    double price;

    public StockData(String ticker, LocalDate timestamp, double price) {
        this.ticker = ticker;
        this.timestamp = timestamp;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }
}
