package entity;

import java.time.LocalDate;

/**
 * An entity representing a transaction.
 */
public class Transaction {
    private final String portfolioId;
    private final String stockTicker;
    private final int quantity;
    private final LocalDate timestamp;
    private final double price;

    public Transaction(String portfolioId, String stockTicker, int quantity, LocalDate timestamp, double price) {
        this.portfolioId = portfolioId;
        this.stockTicker = stockTicker;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.price = price;
    }

    public String getPortfolioId() {
        return portfolioId;
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
