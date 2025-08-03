package use_case.recommend;

import entity.SortingUtil;
import entity.StockData;
import entity.FinancialCalculator;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendInteractor implements RecommendInputBoundary {
    private final RecommendStockDataAccessInterface stockDataAccessInterface;
    private final RecommendTransactionDataAccessInterface transactionDataAccessInterface;
    private final RecommendOutputBoundary recommendOutputBoundary;
    private static final int N = 3;

    public RecommendInteractor(RecommendStockDataAccessInterface stockDataAccessInterface, RecommendTransactionDataAccessInterface transactionDataAccessInterface,
                               RecommendOutputBoundary recommendOutputBoundary) {
        this.stockDataAccessInterface = stockDataAccessInterface;
        this.transactionDataAccessInterface = transactionDataAccessInterface;
        this.recommendOutputBoundary = recommendOutputBoundary;
    }

    @Override
    public void execute(RecommendInputData recommendInputData) {
        Set<String> tickers = stockDataAccessInterface.getAvailableTickers();
        Set<String> portfolioTickers = transactionDataAccessInterface.getPortfolioTickers(recommendInputData.getPortfolioId());
        Set<String> nonPortfolioTickers = tickers.stream().filter(p -> !portfolioTickers.contains(p)).collect(Collectors.toSet());
        Map<String, Double> sharpeRatios = new LinkedHashMap<>();
        Map<String, Double> volatility = new LinkedHashMap<>();
        Map<String, Double> pricesMap = new LinkedHashMap<>();
        for (String ticker : tickers) {
            List<StockData> stockData = stockDataAccessInterface.pastStockData(ticker);
            List<Double> prices = stockData.stream().map(StockData::getPrice).collect(Collectors.toList());
            sharpeRatios.put(ticker, FinancialCalculator.sharpeRatio(prices));
            volatility.put(ticker, FinancialCalculator.computeVolatility(prices));
            pricesMap.put(ticker, prices.get(0));
        }
        Map<String, Double> haveRecs = SortingUtil.getTopNByValue(sharpeRatios, N, true, nonPortfolioTickers, pricesMap);
        Map<String, Double> notHaveRecs = SortingUtil.getTopNByValue(sharpeRatios, N, true, portfolioTickers, pricesMap);
        Map<String, Double> stable = SortingUtil.getTopNByValue(volatility, N, false, nonPortfolioTickers, pricesMap);

        recommendOutputBoundary.prepareView(new RecommendOutputData(
                haveRecs, notHaveRecs, stable
        ));
    }

    @Override
    public void routeToPortfolio() {
        recommendOutputBoundary.routeToPortfolio();
    }
}
