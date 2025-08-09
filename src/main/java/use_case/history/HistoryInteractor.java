package use_case.history;

import java.time.LocalDate;
import java.util.List;

import entity.Transaction;

/**
 * Interactor for the History Use Case.
 */
public class HistoryInteractor implements HistoryInputBoundary {
    private final HistoryDataAccessInterface dataAccessInterface;
    private final HistoryOutputBoundary historyOutputBoundary;

    public HistoryInteractor(HistoryDataAccessInterface dataAccessInterface,
                             HistoryOutputBoundary historyOutputBoundary) {
        this.dataAccessInterface = dataAccessInterface;
        this.historyOutputBoundary = historyOutputBoundary;
    }

    @Override
    public void execute(HistoryInputData historyInputData) {
        final String portfolioId = historyInputData.getPortfolioId();
        final List<Transaction> transactions = dataAccessInterface.pastTransactions(portfolioId);
        final int length = transactions.size();
        final String[] tickers = new String[length];
        final int[] amounts = new int[length];
        final double[] prices = new double[length];
        final LocalDate[] dates = new LocalDate[length];

        for (int i = 0; i < length; i++) {
            final Transaction transaction = transactions.get(i);
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
