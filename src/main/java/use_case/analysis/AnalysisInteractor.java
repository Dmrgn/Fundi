package use_case.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import entity.FinancialCalculator;
import entity.SortingUtil;
import entity.StockData;
import entity.Transaction;

/**
 * The Interactor for the Analysis Use Case.
 */
public class AnalysisInteractor implements AnalysisInputBoundary {
    private static final int N = 2;
    private final AnalysisStockDataAccessInterface stockDataAccessInterface;
    private final AnalysisTransactionDataAccessInterface transactionDataAccessInterface;
    private final AnalysisOutputBoundary analysisOutputBoundary;

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
        Map<String, Integer> tickerAmounts = FinancialCalculator.getTickerAmounts(transactions);
        int totalStocks = FinancialCalculator.getTotalAmount(tickerAmounts);
        Map<String, Double> percentages = new HashMap<>();
        double totalVol = 0;
        Map<String, Double> vols = new HashMap<>();
        double totalReturn = 0;
        Map<String, Double> returns = new HashMap<>();

        for (String ticker : tickerAmounts.keySet()) {
            percentages.put(ticker, FinancialCalculator.computePercentage(totalStocks, tickerAmounts.get(ticker)));
            List<StockData> stockData = stockDataAccessInterface.pastStockData(ticker);
            double vol = FinancialCalculator.computeVolatility(stockData.stream().map(StockData::getPrice).collect(Collectors.toList()));
            totalVol += vol;
            vols.put(ticker, vol);
            double retr = FinancialCalculator.computeReturn(stockData);
            totalReturn += retr * tickerAmounts.get(ticker);
            returns.put(ticker, retr);
        }
        totalVol = totalVol / totalStocks;
        totalReturn = totalReturn / totalStocks;

        analysisOutputBoundary.prepareView(new AnalysisOutputData(
                totalStocks,
                SortingUtil.getTopnByValue(percentages, N, true),
                totalVol,
                SortingUtil.getTopnByValue(vols, N, true),
                SortingUtil.getTopnByValue(vols, N, false),
                totalReturn,
                SortingUtil.getTopnByValue(returns, N, true),
                SortingUtil.getTopnByValue(returns, N, false)
        ));
    }

    @Override
    public void routeToPortfolio() {
        analysisOutputBoundary.routeToPortfolio();
    }
}
