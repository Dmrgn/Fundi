package data_access;

import entity.Transaction;
import use_case.analysis.AnalysisDataAccessInterface;
import use_case.buy.BuyTransactionDataAccessInterface;
import use_case.history.HistoryDataAccessInterface;
import use_case.portfolio.PortfolioDataAccessInterface;
import use_case.sell.SellTransactionDataAccessInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * DAO for user data implemented using a Database to persist the data.
 */
public class DBTransactionDataAccessObject implements AnalysisDataAccessInterface, BuyTransactionDataAccessInterface,
        SellTransactionDataAccessInterface, HistoryDataAccessInterface, PortfolioDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, List<Transaction>> transactions = new HashMap<>();

    public DBTransactionDataAccessObject() throws SQLException {
        String query = """
                SELECT t.portfolio_id as portfolio_id,
                       t.stock_name as stock_name,
                       t.amount as amount,
                       t.price as price,
                       t.date as date
                FROM transactions t
                """;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String portfolioId = resultSet.getString("portfolio_id");
                String ticker = resultSet.getString("stock_name");
                double price = resultSet.getDouble("price");
                int amount = resultSet.getInt("amount");
                LocalDate date = resultSet.getDate("date").toLocalDate();

                if (!transactions.containsKey(portfolioId)) {
                    transactions.put(portfolioId, new ArrayList<>());
                }
                transactions.get(portfolioId).add(new Transaction(
                        portfolioId,
                        ticker,
                        amount,
                        date,
                        price
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Transaction> pastTransactions(String portfolioId) {
        return transactions.get(portfolioId);
    }

    @Override
    public double valueOfTicker(String portfolioId, String ticker) {
        double total = 0;
        if (transactions.containsKey(portfolioId)) {
            for (Transaction transaction : transactions.get(portfolioId)) {
                if (Objects.equals(transaction.getStockTicker(), ticker)) {
                    total += transaction.getPrice() * transaction.getQuantity();
                }
            }
        }
        return total;
    }

    @Override
    public void save(Transaction transaction) {
        saveToDB(transaction);
        if (!transactions.containsKey(transaction.getPortfolioId())) {
            transactions.put(transaction.getPortfolioId(), new ArrayList<>());
        }
        transactions.get(transaction.getPortfolioId()).add(transaction);
    }

    private void saveToDB(Transaction transaction) {
        String query = """
                INSERT INTO transactions(portfolio_id, stock_name, amount, date, price)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, transaction.getPortfolioId());
            pstmt.setString(2, transaction.getStockTicker());
            pstmt.setInt(3, transaction.getQuantity());
            pstmt.setDate(4, java.sql.Date.valueOf(transaction.getTimestamp()));
            pstmt.setDouble(5, transaction.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}