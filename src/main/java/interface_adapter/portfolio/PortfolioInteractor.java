package interface_adapter.portfolio;

import entity.Transaction;
import use_case.portfolio.*;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * The Portfolio Interactor.
 */
public class PortfolioInteractor implements PortfolioInputBoundary {
    private final PortfolioDataAccessInterface portfolioDataAccessObject;
    private final PortfolioOutputBoundary portfolioPresenter;

    public PortfolioInteractor(PortfolioDataAccessInterface portfolioDataAccessInterface,
                           PortfolioOutputBoundary portfolioPresenter) {
        this.portfolioDataAccessObject = portfolioDataAccessInterface;
        this.portfolioPresenter = portfolioPresenter;
    }

    @Override
    public void execute(PortfolioInputData portfolioInputData) {
        final String portfolioId = portfolioInputData.getPortfolioId();
        final String portfolioName = portfolioInputData.getPortfolioName();
        List<Transaction> portfolio = portfolioDataAccessObject.pastTransactions(portfolioId);
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
                values.put(ticker, values.get(ticker) + transaction.getPrice() * transaction.getQuantity());
                if (values.get(ticker) == 0.0) { // Remove empty tickers
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
                values.put(ticker, transaction.getPrice() * transaction.getQuantity());
                amounts.put(ticker, transaction.getQuantity());
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

    @Override
    public void routeToBuy(String portfolioId) {
        portfolioPresenter.routeToBuy(portfolioId);
    }

    @Override
    public void routeToSell(String portfolioId) {
        portfolioPresenter.routeToSell(portfolioId);
    }
}