package data_access;

import entity.Transaction;
import use_case.dashboard.DashboardDataAccessInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Data access object for dashboard functionality.
 */
public class DBDashboardDataAccessObject implements DashboardDataAccessInterface {
    private final DBPortfoliosDataAccessObject portfoliosDAO;
    private final DBTransactionDataAccessObject transactionDAO;
    private final DBStockDataAccessObject stockDAO;

    public DBDashboardDataAccessObject() throws SQLException {
        this.portfoliosDAO = new DBPortfoliosDataAccessObject();
        this.transactionDAO = new DBTransactionDataAccessObject();
        this.stockDAO = new DBStockDataAccessObject();
    }

    @Override
    public Map<String, String> getPortfolios(String username) {
        return portfoliosDAO.getPortfolios(username);
    }

    @Override
    public List<Transaction> getTransactions(String portfolioId) {
        return transactionDAO.pastTransactions(portfolioId);
    }

    @Override
    public double getCurrentStockPrice(String ticker) {
        if (stockDAO.hasTicker(ticker)) {
            return stockDAO.getPrice(ticker);
        }
        // Return a default price if ticker not found
        return 100.0;
    }
}
