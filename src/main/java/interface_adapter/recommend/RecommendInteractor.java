package interface_adapter.recommend;

import entity.StockData;
import use_case.recommend.*;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendInteractor implements RecommendInputBoundary {
    private RecommendDataAccessInterface dataAccessInterface;
    private RecommendOutputBoundary recommendOutputBoundary;
    private static final int n = 3;

    public RecommendInteractor(RecommendDataAccessInterface dataAccessInterface, RecommendOutputBoundary recommendOutputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.recommendOutputBoundary = recommendOutputBoundary;
    }

    @Override
    public void execute(RecommendInputData recommendInputData) {
        String[] tickers = dataAccessInterface.getAvailableTickers();
        Map<String, Double> sharpeRatios = new LinkedHashMap<>();
        for (String ticker : tickers) {
            List<StockData> stockData = dataAccessInterface.pastStockData(ticker);
            List<Double> prices = stockData.stream().map(StockData::getPrice).collect(Collectors.toList());
            List<Double> returns = toReturns(prices);
            double avgReturn = computeMean(returns);
            double volatility = computeVolatility(returns, avgReturn); // Multiply by 100 for stability RM magic number later
            sharpeRatios.put(ticker, avgReturn / volatility);
        }
        Map<String, Double> topN = getTopNByValue(sharpeRatios, n, true);
        String[] topTickers = topN.keySet().toArray(new String[0]);
        double[] topPrices = new double[topTickers.length];
        for (int i = 0; i < topTickers.length; i++) {
            topPrices[i] = dataAccessInterface.pastStockData(topTickers[i]).get(0).getPrice();
            System.out.println(topTickers[i] + ": " + sharpeRatios.get(topTickers[i]));
        }
        recommendOutputBoundary.prepareView(
                new RecommendOutputData(
                        topTickers,
                        topPrices
                )
        );
    }

    private List<Double> toReturns(List<Double> prices) {
        List<Double> returns = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            double prev = prices.get(i - 1);
            double curr = prices.get(i);
            returns.add((curr - prev) / prev);
        }
        return returns;
    }

    private double computeMean(List<Double> values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private double computeVolatility(List<Double> returns, double mean) {
        double sum = 0;
        for (double value : returns) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sum / (returns.size() - 1));
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
        recommendOutputBoundary.routeToPortfolio();
    }
}
