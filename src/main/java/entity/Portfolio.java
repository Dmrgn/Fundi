package entity;

import java.util.List;
import java.util.ArrayList;

/**
 * The representation of a portfolio.
 */
public class Portfolio {

    private final List<Stock> stocks;

    public Portfolio() {
        stocks = new ArrayList<>();
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }
}