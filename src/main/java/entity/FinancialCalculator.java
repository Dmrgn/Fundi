package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class containing helper functions for financial calculations.
 */
public final class FinancialCalculator {
    private static final double PERCENT = 100;
    private static final double TOLERANCE = 1e-8;

    private FinancialCalculator() {

    }

    /**
     * Return a mapping from each ticker in the transaction list to its
     * corresponding total amount.
     * 
     * @param transactions The list of transactions to parse
     * @return The mapping from ticker to amount
     */
    public static Map<String, Integer> getTickerAmounts(List<Transaction> transactions) {
        Map<String, Integer> tickerAmounts = new HashMap<>();
        for (Transaction transaction : transactions) {
            String ticker = transaction.getStockTicker();
            int quantity = transaction.getQuantity();
            if (transaction.getPrice() < 0) {
                quantity = quantity * -1;
            }
            System.out.println("Processing transaction: " + transaction);
            System.out.println("Ticker: " + ticker + ", Quantity: " + quantity);
            System.out.println("Current Ticker Amounts: " + tickerAmounts);
            if (!tickerAmounts.containsKey(ticker)) {
                tickerAmounts.put(ticker, quantity);
            }

            else {
                tickerAmounts.put(ticker, tickerAmounts.get(ticker) + quantity);
            }

            if (tickerAmounts.get(ticker) == 0) {
                tickerAmounts.remove(ticker);
            }
        }
        return tickerAmounts;
    }

    /**
     * The total amount of tickers in the ticker mapping.
     * 
     * @param tickerAmounts A mapping from ticker to amount
     * @return The total number of tickers
     */
    public static int getTotalAmount(Map<String, Integer> tickerAmounts) {
        return tickerAmounts.keySet().stream().mapToInt(tickerAmounts::get).sum();
    }

    /**
     * Compute what percentage the given amount is of the total amount.
     * 
     * @param totalAmount The total
     * @param amount      The part to compute the percentage for
     * @return The percentage
     */
    public static double computePercentage(int totalAmount, int amount) {
        return (double) amount / totalAmount * PERCENT;
    }

    /**
     * Compute the volatility of a price time series for a single ticker.
     * 
     * @param prices The price time series
     * @return The volatility
     */
    public static double computeVolatility(List<Double> prices) {
        List<Double> returns = computeReturns(prices);
        double mean = computeMean(returns);
        double vol = 0;
        for (double retr : returns) {
            vol += Math.pow(retr - mean, 2);
        }
        return vol / returns.size();
    }

    /**
     * Compute the return timeseries of price time series.
     * 
     * @param prices The price time series
     * @return The return time series
     */
    public static List<Double> computeReturns(List<Double> prices) {
        List<Double> returns = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            double prev = prices.get(i - 1);
            double cur = prices.get(i);
            returns.add((cur - prev) / prev);
        }
        return returns;
    }

    /**
     * Compute the mean of a list.
     * 
     * @param values The list
     * @return The mean
     */
    public static double computeMean(List<Double> values) {
        double sum = 0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    /**
     * Compute the return of a time series of stockdata.
     * 
     * @param stockData The stock data time series
     * @return The total return
     */
    public static double computeReturn(List<StockData> stockData) {
        // Enforce sorting
        List<StockData> sortedStockData = stockData.stream()
                .sorted(Comparator.comparing(StockData::getTimestamp)).toList();
        double latestPrice = sortedStockData.get(stockData.size() - 1).getPrice();
        double earliestPrice = sortedStockData.get(0).getPrice();
        return (latestPrice - earliestPrice) / earliestPrice * PERCENT;
    }

    /**
     * Compute the Sharpe Ratio of a price time series.
     * 
     * @param prices The price time series
     * @return The Sharpe Ratio of the time series
     */
    public static double sharpeRatio(List<Double> prices) {
        List<Double> returns = FinancialCalculator.computeReturns(prices);
        double avgReturn = computeMean(returns);

        double vol = computeVolatility(returns);

        return avgReturn / (vol + TOLERANCE);
    }
}
