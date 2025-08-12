package usecase.dashboard;

import entity.PortfolioValuePoint;
import entity.Transaction;

import java.time.LocalDate;
import java.util.*;

/**
 * The Dashboard Interactor.
 */
public class DashboardInteractor implements DashboardInputBoundary {
    private final DashboardDataAccessInterface dashboardDataAccessObject;
    private final DashboardOutputBoundary dashboardPresenter;

    public DashboardInteractor(DashboardDataAccessInterface dashboardDataAccessInterface,
            DashboardOutputBoundary dashboardOutputBoundary) {
        this.dashboardDataAccessObject = dashboardDataAccessInterface;
        this.dashboardPresenter = dashboardOutputBoundary;
    }

    @Override
    public void execute(DashboardInputData dashboardInputData) {
        try {
            String username = dashboardInputData.getUsername();
            Map<String, String> portfolios = dashboardDataAccessObject.getPortfolios(username);

            List<PortfolioValuePoint> allValuePoints = new ArrayList<>();
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(30);

            for (Map.Entry<String, String> portfolio : portfolios.entrySet()) {
                String portfolioName = portfolio.getKey();
                String portfolioId = portfolio.getValue();

                List<Transaction> transactions = dashboardDataAccessObject.getTransactions(portfolioId);
                List<PortfolioValuePoint> portfolioValueHistory = calculatePortfolioValueHistory(
                        portfolioId, portfolioName, transactions, startDate, endDate);

                allValuePoints.addAll(portfolioValueHistory);
            }

            DashboardOutputData dashboardOutputData = new DashboardOutputData(allValuePoints, false);
            dashboardPresenter.prepareSuccessView(dashboardOutputData);

        } catch (Exception e) {
            dashboardPresenter.prepareFailView("Failed to load portfolio value history: " + e.getMessage());
        }
    }

    private List<PortfolioValuePoint> calculatePortfolioValueHistory(String portfolioId, String portfolioName,
            List<Transaction> transactions,
            LocalDate startDate, LocalDate endDate) {
        List<PortfolioValuePoint> valuePoints = new ArrayList<>();

        // Sort transactions by date
        transactions.sort(Comparator.comparing(Transaction::getTimestamp));

        // Track holdings over time
        Map<String, Integer> holdings = new HashMap<>();

        // Apply all transactions before start date to get initial holdings
        for (Transaction transaction : transactions) {
            if (transaction.getTimestamp().isBefore(startDate)) {
                String ticker = transaction.getStockTicker();
                int quantity = transaction.getQuantity();

                // Positive price means buy, negative means sell
                if (transaction.getPrice() > 0) {
                    holdings.put(ticker, holdings.getOrDefault(ticker, 0) + quantity);
                } else {
                    holdings.put(ticker, holdings.getOrDefault(ticker, 0) - quantity);
                }
            }
        }

        // Track which transactions we've already processed
        int transactionIndex = 0;
        while (transactionIndex < transactions.size() &&
                transactions.get(transactionIndex).getTimestamp().isBefore(startDate)) {
            transactionIndex++;
        }

        // Calculate value for each day in the range
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Apply transactions that occur on this date
            while (transactionIndex < transactions.size() &&
                    transactions.get(transactionIndex).getTimestamp().equals(date)) {
                Transaction transaction = transactions.get(transactionIndex);
                String ticker = transaction.getStockTicker();
                int quantity = transaction.getQuantity();

                // Positive price means buy, negative means sell
                if (transaction.getPrice() > 0) {
                    holdings.put(ticker, holdings.getOrDefault(ticker, 0) + quantity);
                } else {
                    holdings.put(ticker, holdings.getOrDefault(ticker, 0) - quantity);
                }
                transactionIndex++;
            }

            // Calculate total portfolio value for this date
            double totalValue = 0.0;
            for (Map.Entry<String, Integer> holding : holdings.entrySet()) {
                String ticker = holding.getKey();
                int quantity = holding.getValue();

                if (quantity > 0) {
                    double currentPrice = dashboardDataAccessObject.getCurrentStockPrice(ticker);
                    totalValue += quantity * currentPrice;
                }
            }

            valuePoints.add(new PortfolioValuePoint(portfolioId, portfolioName, date, totalValue));
        }

        return valuePoints;
    }
}
