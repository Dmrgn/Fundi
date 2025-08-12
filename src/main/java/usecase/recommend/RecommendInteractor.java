package usecase.recommend;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import entity.FinancialCalculator;
import entity.SortingUtil;
import entity.StockData;

/**
 * Interactor for the Recommend Use Case.
 */
public class RecommendInteractor implements RecommendInputBoundary {
    private static final int N = 3;
    private final RecommendStockDataAccessInterface stockDataAccessInterface;
    private final RecommendTransactionDataAccessInterface transactionDataAccessInterface;
    private final RecommendOutputBoundary recommendOutputBoundary;

    public RecommendInteractor(RecommendStockDataAccessInterface stockDataAccessInterface,
                               RecommendTransactionDataAccessInterface transactionDataAccessInterface,
                               RecommendOutputBoundary recommendOutputBoundary) {
        this.stockDataAccessInterface = stockDataAccessInterface;
        this.transactionDataAccessInterface = transactionDataAccessInterface;
        this.recommendOutputBoundary = recommendOutputBoundary;
    }

    /**
     * Execute the Recommend Use Case.
     * @param recommendInputData the input data
     */
    @Override
    public void execute(RecommendInputData recommendInputData) {
        final Set<String> tickers = stockDataAccessInterface.getAvailableTickers();
        final Set<String> portfolioTickers = transactionDataAccessInterface
                .getPortfolioTickers(recommendInputData.getPortfolioId());
        final Set<String> nonPortfolioTickers = tickers.stream().filter(port -> !portfolioTickers.contains(port))
                .collect(Collectors.toSet());
        final Map<String, Double> sharpeRatios = new LinkedHashMap<>();
        final Map<String, Double> volatility = new LinkedHashMap<>();
        final Map<String, Double> pricesMap = new LinkedHashMap<>();
        for (String ticker : tickers) {
            final List<StockData> stockData = stockDataAccessInterface.pastStockData(ticker);
            final List<Double> prices = stockData.stream().map(StockData::getPrice).collect(Collectors.toList());
            sharpeRatios.put(ticker, FinancialCalculator.sharpeRatio(prices));
            volatility.put(ticker, FinancialCalculator.computeVolatility(prices, true));
            pricesMap.put(ticker, prices.get(0));
        }
        final Map<String, Double> haveRecs = SortingUtil.getTopnByValue(
                sharpeRatios, N, true, nonPortfolioTickers, pricesMap);
        final Map<String, Double> notHaveRecs = SortingUtil.getTopnByValue(
                sharpeRatios, N, true, portfolioTickers, pricesMap);
        final Map<String, Double> stable = SortingUtil.getTopnByValue(
                volatility, N, false, nonPortfolioTickers, pricesMap);

        recommendOutputBoundary.prepareView(new RecommendOutputData(
                haveRecs, notHaveRecs, stable
        ));
    }

    /**
     * Switch to the Portfolio View.
     */
    @Override
    public void routeToPortfolio() {
        recommendOutputBoundary.routeToPortfolio();
    }
}
