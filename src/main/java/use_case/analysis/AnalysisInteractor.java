package use_case.analysis;

import entity.StockData;
import entity.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class AnalysisInteractor implements AnalysisInputBoundary {
    private final AnalysisStockDataAccessInterface stockDataAccessInterface;
    private final AnalysisTransactionDataAccessInterface transactionDataAccessInterface;
    private final AnalysisOutputBoundary analysisOutputBoundary;
    private static final int n = 2;

    public AnalysisInteractor(AnalysisStockDataAccessInterface stockDataAccessInterface,
                              AnalysisTransactionDataAccessInterface analysisTransactionDataAccessInterface,
                              AnalysisOutputBoundary analysisOutputBoundary) {
        this.stockDataAccessInterface = stockDataAccessInterface;
        this.transactionDataAccessInterface = analysisTransactionDataAccessInterface;
        this.analysisOutputBoundary = analysisOutputBoundary;
    }

    @Override
    public void execute(AnalysisInputData analysisInputData) {
        String portfolioId = analysisInputData.getPortfolioId();
        List<Transaction> transactions = transactionDataAccessInterface.pastTransactions(portfolioId);
        Map<String, Integer> tickerAmounts = getTickerAmounts(transactions);
        int totalStocks = getTotalAmount(tickerAmounts);
        Map<String, Double> percentages = new HashMap<>();
        double totalVol = 0;
        Map<String, Double> vols = new HashMap<>();
        double totalReturn = 0;
        Map<String, Double> returns = new HashMap<>();

        for (String ticker : tickerAmounts.keySet()) {
            percentages.put(ticker, computePercentage(totalStocks, tickerAmounts.get(ticker)));
            List<StockData> stockData = stockDataAccessInterface.getPastPrices(ticker);
            double vol = computeVolatility(stockData.stream().map(StockData::getPrice).collect(Collectors.toList()));
            totalVol += vol;
            vols.put(ticker, vol);
            double retr = computeReturn(stockData);
            totalReturn += retr * tickerAmounts.get(ticker);
            returns.put(ticker, retr);
        }
        totalVol = totalVol / totalStocks;
        totalReturn = totalReturn / totalStocks;

        analysisOutputBoundary.prepareView(new AnalysisOutputData(
                totalStocks,
                getTopNByValue(percentages, n, true),
                totalVol,
                getTopNByValue(vols, n, true),
                getTopNByValue(vols, n, false),
                totalReturn,
                getTopNByValue(returns, n, true),
                getTopNByValue(returns, n, false)
        ));
    }

    private Map<String, Integer> getTickerAmounts(List<Transaction> transactions) {
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

    private static int getTotalAmount(Map<String, Integer> tickerAmounts) {
        return tickerAmounts.keySet().stream().mapToInt(tickerAmounts::get).sum();
    }

    private static double computePercentage(int totalAmount, int amount) {
        return (double) amount / totalAmount * 100;
    }


    private static double computeVolatility(List<Double> prices) {
        List<Double> returns = computeReturns(prices);
        double mean = computeMean(returns);
        double vol = 0;
        for (double retr : returns) {
            vol += Math.pow(retr - mean, 2);
        }
        return vol / returns.size();
    }

    private static List<Double> computeReturns(List<Double> prices) {
        List<Double> returns = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            double prev = prices.get(i - 1);
            double cur = prices.get(i);
            returns.add((cur - prev) / prev);
        }
        return returns;
    }

    private static double computeMean(List<Double> values) {
        double sum = 0;
        for (Double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private static double computeReturn(List<StockData> stockData) {
        // Enforce sorting
        List<StockData> sortedStockData = stockData.stream().
                sorted(Comparator.comparing(StockData::getTimestamp)).toList();
        double latestPrice = sortedStockData.get(stockData.size() - 1).getPrice();
        double earliestPrice = sortedStockData.get(0).getPrice();
        return (latestPrice - earliestPrice) / earliestPrice * 100;
    }

    private static <K, V extends Number> Map<K, V> getTopNByValue(Map<K, V> map, int n, boolean descending) {
        Comparator<Map.Entry<K, V>> comparator = Comparator.comparingDouble(e -> e.getValue().doubleValue());
        if (descending) {
            comparator = comparator.reversed();
        }

        return map.entrySet().stream()
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Override
    public void routeToPortfolio() {
        analysisOutputBoundary.routeToPortfolio();
    }
}
