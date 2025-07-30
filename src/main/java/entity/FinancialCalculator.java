package entity;

import java.util.*;
import java.util.stream.Collectors;

public class FinancialCalculator {
    private FinancialCalculator() {

    }

    public static Map<String, Integer> getTickerAmounts(List<Transaction> transactions) {
        Map<String, Integer> tickerAmounts = new HashMap<>();
        for (Transaction transaction : transactions) {
            String ticker = transaction.getStockTicker();
            int quantity = transaction.getQuantity();
            if (transaction.getPrice() < 0) {
                quantity = quantity * -1;
            }
            if (!tickerAmounts.containsKey(ticker)) {
                tickerAmounts.put(ticker, quantity);
            } else {
                tickerAmounts.put(ticker, tickerAmounts.get(ticker) + quantity);
            }

            if (tickerAmounts.get(ticker) == 0) {
                tickerAmounts.remove(ticker);
            }
        }
        return tickerAmounts;
    }

    public static int getTotalAmount(Map<String, Integer> tickerAmounts) {
        return tickerAmounts.keySet().stream().mapToInt(tickerAmounts::get).sum();
    }

    public static double computePercentage(int totalAmount, int amount) {
        return (double) amount / totalAmount * 100;
    }


    public static double computeVolatility(List<Double> prices) {
        List<Double> returns = computeReturns(prices);
        double mean = computeMean(returns);
        double vol = 0;
        for (double retr : returns) {
            vol += Math.pow(retr - mean, 2);
        }
        return vol / returns.size();
    }

    public static List<Double> computeReturns(List<Double> prices) {
        List<Double> returns = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            double prev = prices.get(i - 1);
            double cur = prices.get(i);
            returns.add((cur - prev) / prev);
        }
        return returns;
    }

    public static double computeMean(List<Double> values) {
        double sum = 0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    public static double computeReturn(List<StockData> stockData) {
        // Enforce sorting
        List<StockData> sortedStockData = stockData.stream().
                sorted(Comparator.comparing(StockData::getTimestamp)).toList();
        double latestPrice = sortedStockData.get(stockData.size() - 1).getPrice();
        double earliestPrice = sortedStockData.get(0).getPrice();
        return (latestPrice - earliestPrice) / earliestPrice * 100;
    }

    public static double sharpeRatio(List<Double> prices) {
        List<Double> returns = FinancialCalculator.computeReturns(prices);
        double avgReturn = computeMean(returns);

        double vol = computeVolatility(returns);

        return avgReturn / (vol + 1e-8);
    }
}
