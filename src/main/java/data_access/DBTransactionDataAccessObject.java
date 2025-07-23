package data_access;

import entity.Transaction;
import use_case.analysis.AnalysisDataAccessInterface;
import use_case.buy.BuyTransactionDataAccessInterface;
import use_case.history.HistoryDataAccessInterface;
import use_case.sell.SellTransactionDataAccessInterface;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO for user data implemented using a Database to persist the data.
 */
public class DBTransactionDataAccessObject implements AnalysisDataAccessInterface, BuyTransactionDataAccessInterface,
        SellTransactionDataAccessInterface, HistoryDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, Map<String, List<Transaction>>> transactions = new HashMap<>();

    public DBTransactionDataAccessObject() throws SQLException {
    }

    @Override
    public Map<String, List<Transaction>> pastTransactions() {
        return Map.of();
    }

    @Override
    public void addTransaction(Transaction transaction) {

    }

    @Override
    public List<Transaction> getHistory() {
        return List.of();
    }
}