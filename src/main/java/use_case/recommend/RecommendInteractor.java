package use_case.recommend;

import entity.StockData;
import entity.FinancialCalculator;

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
            List<Double> returns = FinancialCalculator.computeReturns(prices);
            double avgReturn = FinancialCalculator.computeMean(returns);
            double volatility = FinancialCalculator.computeVolatility(returns);
            sharpeRatios.put(ticker, avgReturn / volatility);
        }
        Map<String, Double> topN = FinancialCalculator.getTopNByValue(sharpeRatios, n, true);
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

    @Override
    public void routeToPortfolio() {
        recommendOutputBoundary.routeToPortfolio();
    }
}
