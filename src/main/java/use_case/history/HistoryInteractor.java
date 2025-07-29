package use_case.history;

import entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public class HistoryInteractor implements HistoryInputBoundary {
    private HistoryDataAccessInterface dataAccessInterface;
    private HistoryOutputBoundary historyOutputBoundary;

    public HistoryInteractor(HistoryDataAccessInterface dataAccessInterface, HistoryOutputBoundary historyOutputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.historyOutputBoundary = historyOutputBoundary;
    }

    @Override
    public void execute(HistoryInputData historyInputData) {
        String portfolioId = historyInputData.getPortfolioId();
        List<Transaction> transactions = dataAccessInterface.pastTransactions(portfolioId);
        int length = transactions.size();
        String[] tickers = new String[length];
        int[] amounts = new int[length];
        double[] prices = new double[length];
        LocalDate[] dates = new LocalDate[length];

        for (int i = 0; i < length; i++) {
            Transaction transaction = transactions.get(i);
            tickers[i] = transaction.getStockTicker();
            amounts[i] = transaction.getQuantity();
            prices[i] = transaction.getPrice();
            dates[i] = transaction.getTimestamp();
        }
        historyOutputBoundary.prepareView(new HistoryOutputData(
                tickers,
                prices,
                amounts,
                dates
        ));
    }

    @Override
    public void routeToPortfolio() {
        historyOutputBoundary.routeToPortfolio();
    }
}
