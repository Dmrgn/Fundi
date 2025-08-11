package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import entity.Transaction;
import use_case.analysis.AnalysisTransactionDataAccessInterface;
import use_case.buy.BuyTransactionDataAccessInterface;
import use_case.history.HistoryDataAccessInterface;
import use_case.portfolio.PortfolioTransactionDataAccessInterface;
import use_case.recommend.RecommendTransactionDataAccessInterface;
import use_case.sell.SellTransactionDataAccessInterface;

/**
 * DAO for transaction data implemented using a Database to persist the data.
 */
public class DBTransactionDataAccessObject implements AnalysisTransactionDataAccessInterface,
        BuyTransactionDataAccessInterface, SellTransactionDataAccessInterface,
        HistoryDataAccessInterface, PortfolioTransactionDataAccessInterface,
        RecommendTransactionDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, List<Transaction>> transactions = new HashMap<>();

    /**
     * Load the transaction data into memory.
     * 
     * @throws SQLException If SQL connection fails
     */
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
                        price));
            }
        }

        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }

    /**
     * The past transactions for the given portfolio.
     * 
     * @param portfolioId the id to search at
     * @return A list of previous transactions
     */
    @Override
    public List<Transaction> pastTransactions(String portfolioId) {
        if (!transactions.containsKey(portfolioId)) {
            return new ArrayList<>();
        }
        return transactions.get(portfolioId);
    }

    /**
     * The amount of a given ticker in the portfolio.
     * 
     * @param portfolioId The portfolio to look at
     * @param ticker      The ticker to look for
     * @return The amount of the given ticker in the portfolio
     */
    @Override
    public int amountOfTicker(String portfolioId, String ticker) {
        int total = 0;
        if (transactions.containsKey(portfolioId)) {
            for (Transaction transaction : transactions.get(portfolioId)) {
                if (Objects.equals(transaction.getStockTicker(), ticker)) {
                    if (transaction.getPrice() > 0) {
                        total += transaction.getQuantity();
                    }

                    else {
                        total -= transaction.getQuantity();
                    }
                }
            }
        }
        return total;
    }

    /**
     * Save a transaction into the DAO.
     * 
     * @param transaction The transaction to save
     */
    @Override
    public void save(Transaction transaction) {
        saveToDB(transaction);
        if (!transactions.containsKey(transaction.getPortfolioId())) {
            transactions.put(transaction.getPortfolioId(), new ArrayList<>());
        }
        transactions.get(transaction.getPortfolioId()).add(transaction);
    }

    private void saveToDB(Transaction transaction) {
        String insertTransaction = """
                INSERT INTO transactions(portfolio_id, stock_name, amount, date, price)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(insertTransaction)) {
            pstmt.setString(1, transaction.getPortfolioId());
            pstmt.setString(2, transaction.getStockTicker());
            pstmt.setInt(3, transaction.getQuantity());
            pstmt.setDate(4, java.sql.Date.valueOf(transaction.getTimestamp()));
            pstmt.setDouble(5, transaction.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        // Update holdings table
        int delta = transaction.getPrice() > 0 ? transaction.getQuantity() : -transaction.getQuantity();
        String updateHoldings = """
                INSERT INTO holdings (portfolio_id, ticker, quantity)
                VALUES (?, ?, ?)
                ON CONFLICT(portfolio_id, ticker) DO UPDATE SET quantity = MAX(0, quantity + excluded.quantity)
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(updateHoldings)) {
            pstmt.setString(1, transaction.getPortfolioId());
            pstmt.setString(2, transaction.getStockTicker());
            pstmt.setInt(3, delta);
            pstmt.executeUpdate();

        } catch (SQLException exception) {
            System.out.println("Holdings update error: " + exception.getMessage());
        }
        
        // Delete holdings with quantity <= 0
        String cleanupHoldings = "DELETE FROM holdings WHERE quantity <= 0";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(cleanupHoldings);
        } catch (SQLException exception) {
            System.out.println("Holdings cleanup error: " + exception.getMessage());
        }
    }

    /**
     * Get all of the tickers in the portfolio.
     * 
     * @param portfolioId The portfolio to look at
     * @return A set of tickers in the portfolio
     */
    @Override
    public Set<String> getPortfolioTickers(String portfolioId) {
        if (!transactions.containsKey(portfolioId)) {
            return new HashSet<>();
        }
        return pastTransactions(portfolioId).stream()
                .map(Transaction::getStockTicker)
                .filter(ticker -> amountOfTicker(portfolioId, ticker) > 0)
                .collect(Collectors.toSet());
    }

    /**
     * Remove a transaction from the DAO.
     * (For testing only)
     * 
     * @param portfolioId The portfolio Id
     * @param ticker      The ticker
     * @param amount      The amount
     */
    public void remove(String portfolioId, String ticker, int amount) {
        if (transactions.containsKey(portfolioId)) {
            List<Transaction> transactionList = transactions.get(portfolioId);
            for (Transaction transaction : transactionList) {
                if (Objects.equals(transaction.getStockTicker(), ticker) && transaction.getQuantity() == amount) {
                    transactions.remove(transaction.getPortfolioId());
                }
            }
        }

        String query = """
                DELETE FROM transactions
                WHERE portfolio_id = ? and stock_name = ? and amount = ?
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, portfolioId);
            pstmt.setString(2, ticker);
            pstmt.setInt(3, amount);
            pstmt.executeUpdate();
        }

        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    /**
     * Whether or not the transaction specified exists.
     * (For testing only)
     * 
     * @param portfolioId The portfolio Id
     * @param ticker      The ticker to look for
     * @param amount      The amount of the ticker
     * @return Whether or not the transaction exists
     */
    public boolean hasTransaction(String portfolioId, String ticker, int amount) {
        if (transactions.containsKey(portfolioId)) {
            for (Transaction transaction : transactions.get(portfolioId)) {
                if (transaction.getStockTicker().equals(ticker) && transaction.getQuantity() == amount) {
                    return true;
                }
            }
        }
        return false;
    }

	@Override
	public List<String> getUserSymbols(String username) {
        Set<String> symbols = new HashSet<>();

        String sql = """
                SELECT DISTINCT t.stock_name
                FROM transactions t
                JOIN portfolios p ON t.portfolio_id = p.id
                JOIN users u ON p.user_id = u.id
                WHERE u.username = ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                symbols.add(rs.getString("stock_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user symbols: " + e.getMessage());
        }

        return new ArrayList<>(symbols);
    }

	@Override
    public int getCurrentHoldings(String portfolioId, String ticker) {
        final String sql = """
            SELECT COALESCE(SUM(
                CASE WHEN price < 0 THEN -amount ELSE amount END
            ), 0) AS qty
            FROM transactions
            WHERE portfolio_id = ? AND stock_name = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, portfolioId);
            ps.setString(2, ticker);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("qty") : 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Test-only cleanup: remove all rows for a portfolio/ticker.
     */
    public void hardDeleteAllFor(String portfolioId, String stockName) throws SQLException {
        final String sql = "DELETE FROM transactions WHERE portfolio_id = ? AND stock_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, portfolioId);
            ps.setString(2, stockName);
            ps.executeUpdate();
        }
    }
}