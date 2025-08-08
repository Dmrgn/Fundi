package use_case.portfolio;

import entity.Transaction;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Interactor for the Portfolio Use Case.
 */
public class PortfolioInteractor implements PortfolioInputBoundary {
    private final PortfolioTransactionDataAccessInterface transactionDataAccessObject;
    private final PortfolioStockDataAccessInterface stockDataAccessObject;
    private final PortfolioOutputBoundary portfolioPresenter;

    public PortfolioInteractor(PortfolioTransactionDataAccessInterface transactionDataAccessInterface,
                               PortfolioStockDataAccessInterface stockDataAccessInterface,
                               PortfolioOutputBoundary portfolioPresenter) {
        this.transactionDataAccessObject = transactionDataAccessInterface;
        this.stockDataAccessObject = stockDataAccessInterface;
        this.portfolioPresenter = portfolioPresenter;
    }

    /**
     * Execute the Portofolio Use Case
     * @param portfolioInputData the input data.
     */
    @Override
    public void execute(PortfolioInputData portfolioInputData) {
        final String portfolioId = portfolioInputData.getPortfolioId();
        final String portfolioName = portfolioInputData.getPortfolioName();
        List<Transaction> portfolio = transactionDataAccessObject.pastTransactions(portfolioId);
        if (portfolio == null) {
            PortfolioOutputData outputData = new PortfolioOutputData(
                    portfolioInputData.getUsername(),
                    portfolioId,
                    portfolioName,
                    null,
                    null,
                    null
            );
            portfolioPresenter.prepareView(outputData);
            return;
        }

        LinkedHashSet<String> tickers = new LinkedHashSet<>();
        LinkedHashMap<String, Double> values = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> amounts = new LinkedHashMap<>();
        for (Transaction transaction : portfolio) {
            String ticker = transaction.getStockTicker();
            if (tickers.contains(ticker)) {
                values.put(ticker, values.get(ticker) + stockDataAccessObject.getPrice(ticker) *
                                                        Math.signum(transaction.getPrice()) * transaction.getQuantity());
                if (values.get(ticker) <= 0.01) { // Remove empty tickers accounting for rounding error
                    tickers.remove(ticker);
                    values.remove(ticker);
                    amounts.remove(ticker);
                } else if (transaction.getPrice() < 0) {
                    amounts.put(ticker, amounts.get(ticker) - transaction.getQuantity());
                } else {
                    amounts.put(ticker, amounts.get(ticker) + transaction.getQuantity());
                }
            } else {
                tickers.add(ticker);
                values.put(ticker, stockDataAccessObject.getPrice(ticker) *
                                   Math.signum(transaction.getPrice()) * transaction.getQuantity());
                // If first transaction is a short (negative price), store negative quantity
                int signedQty = transaction.getPrice() < 0
                        ? -transaction.getQuantity()
                        : transaction.getQuantity();
                amounts.put(ticker, signedQty);
            }
        }
        PortfolioOutputData outputData = new PortfolioOutputData(
                portfolioInputData.getUsername(),
                portfolioId,
                portfolioName,
                tickers.toArray(new String[0]),
                amounts.values().stream().mapToInt(Integer::intValue).toArray(),
                values.values().stream().mapToDouble(Double::doubleValue).toArray()
        );
        portfolioPresenter.prepareView(outputData);
    }

    /**
     * Switch to the Buy View
     * @param portfolioId The portfolio id to update the state of the Buy View Model
     */
    @Override
    public void routeToBuy(String portfolioId) {
        portfolioPresenter.routeToBuy(portfolioId);
    }

    /**
     * Switch to the Sell View
     * @param portfolioId The portfolio id to update the state of the Sell View Model
     */
    @Override
    public void routeToSell(String portfolioId) {
        portfolioPresenter.routeToSell(portfolioId);
    }

    /**
     * Switch to the Short Sell View
     * @param portfolioId The portfolio id to update the state of the Short Sell View Model
     */
    @Override
    public void routeToShort(String portfolioId) {
        portfolioPresenter.routeToShort(portfolioId);
    }
}