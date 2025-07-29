package use_case.analysis;

import entity.StockData;
import entity.Transaction;
import entity.FinancialCalculator;

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
        Map<String, Integer> tickerAmounts = FinancialCalculator.getTickerAmounts(transactions);
        int totalStocks = FinancialCalculator.getTotalAmount(tickerAmounts);
        Map<String, Double> percentages = new HashMap<>();
        double totalVol = 0;
        Map<String, Double> vols = new HashMap<>();
        double totalReturn = 0;
        Map<String, Double> returns = new HashMap<>();

        for (String ticker : tickerAmounts.keySet()) {
            percentages.put(ticker, FinancialCalculator.computePercentage(totalStocks, tickerAmounts.get(ticker)));
            List<StockData> stockData = stockDataAccessInterface.getPastPrices(ticker);
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
                FinancialCalculator.getTopNByValue(percentages, n, true),
                totalVol,
                FinancialCalculator.getTopNByValue(vols, n, true),
                FinancialCalculator.getTopNByValue(vols, n, false),
                totalReturn,
                FinancialCalculator.getTopNByValue(returns, n, true),
                FinancialCalculator.getTopNByValue(returns, n, false)
        ));
    }

    @Override
    public void routeToPortfolio() {
        analysisOutputBoundary.routeToPortfolio();
    }
}
