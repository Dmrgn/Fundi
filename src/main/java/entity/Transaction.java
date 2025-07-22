package entity;

import java.time.LocalDate;

/**
 * The representation of a stock.
 */
public class Transaction {
    private final String stockTicker;
    private final int quantity;
    private final LocalDate timestamp;
    private final double price;

    public Transaction(String stockTicker, int quantity, LocalDate timestamp, double price) {
        this.stockTicker = stockTicker;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.price = price;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }
}
