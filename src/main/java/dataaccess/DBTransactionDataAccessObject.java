package dataaccess;

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
import usecase.analysis.AnalysisTransactionDataAccessInterface;
import usecase.buy.BuyTransactionDataAccessInterface;
import usecase.history.HistoryDataAccessInterface;
import usecase.portfolio.PortfolioTransactionDataAccessInterface;
import usecase.recommend.RecommendTransactionDataAccessInterface;
import usecase.sell.SellTransactionDataAccessInterface;

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
        // Ensure DB schema exists
        DatabaseInitializer.ensureInitialized();
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
            // Bind portfolio_id as integer when possible
            try {
                pstmt.setInt(1, Integer.parseInt(transaction.getPortfolioId().trim()));
            } catch (NumberFormatException nfe) {
                pstmt.setString(1, transaction.getPortfolioId());
            }
            pstmt.setString(2, transaction.getStockTicker());
            pstmt.setInt(3, transaction.getQuantity());
            pstmt.setDate(4, java.sql.Date.valueOf(transaction.getTimestamp()));
            pstmt.setDouble(5, transaction.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        // Update holdings table (robust against CHECK constraints)
        int delta = transaction.getPrice() > 0 ? transaction.getQuantity() : -transaction.getQuantity();
        final String selectQty = "SELECT quantity FROM holdings WHERE portfolio_id = ? AND UPPER(ticker) = UPPER(?)";
        final String insertQty = "INSERT INTO holdings (portfolio_id, ticker, quantity) VALUES (?, ?, ?)";
        final String updateQty = "UPDATE holdings SET quantity = ? WHERE portfolio_id = ? AND UPPER(ticker) = UPPER(?)";
        final String deleteQty = "DELETE FROM holdings WHERE portfolio_id = ? AND UPPER(ticker) = UPPER(?)";

        try {
            boolean rowExists = false;
            int currentQty = 0;

            // Check if row exists and get current quantity
            try (PreparedStatement sel = connection.prepareStatement(selectQty)) {
                try {
                    sel.setInt(1, Integer.parseInt(transaction.getPortfolioId().trim()));
                } catch (NumberFormatException nfe) {
                    sel.setString(1, transaction.getPortfolioId());
                }
                sel.setString(2, transaction.getStockTicker().trim());
                try (ResultSet rs = sel.executeQuery()) {
                    if (rs.next()) {
                        rowExists = true;
                        currentQty = rs.getInt(1);
                    }
                }
            }

            int newQty = currentQty + delta;

            if (rowExists) {
                if (newQty <= 0) {
                    // Delete the row
                    try (PreparedStatement del = connection.prepareStatement(deleteQty)) {
                        try {
                            del.setInt(1, Integer.parseInt(transaction.getPortfolioId().trim()));
                        } catch (NumberFormatException nfe) {
                            del.setString(1, transaction.getPortfolioId());
                        }
                        del.setString(2, transaction.getStockTicker().trim());
                        int delRows = del.executeUpdate();
                        System.out.println("Deleted holdings row for " + transaction.getPortfolioId() + "/"
                                + transaction.getStockTicker() + " (rows deleted: " + delRows + ")");
                    }
                } else {
                    // Update the row
                    try (PreparedStatement upd = connection.prepareStatement(updateQty)) {
                        upd.setInt(1, newQty);
                        try {
                            upd.setInt(2, Integer.parseInt(transaction.getPortfolioId().trim()));
                        } catch (NumberFormatException nfe) {
                            upd.setString(2, transaction.getPortfolioId());
                        }
                        upd.setString(3, transaction.getStockTicker().trim());
                        int updRows = upd.executeUpdate();
                        System.out.println("Updated holdings for " + transaction.getPortfolioId() + "/"
                                + transaction.getStockTicker() + " to " + newQty + " (rows updated: " + updRows + ")");
                    }
                }
            } else {
                // No existing row - only insert on positive delta (buys)
                if (delta > 0) {
                    try (PreparedStatement ins = connection.prepareStatement(insertQty)) {
                        try {
                            ins.setInt(1, Integer.parseInt(transaction.getPortfolioId().trim()));
                        } catch (NumberFormatException nfe) {
                            ins.setString(1, transaction.getPortfolioId());
                        }
                        ins.setString(2, transaction.getStockTicker().trim());
                        ins.setInt(3, delta);
                        int insRows = ins.executeUpdate();
                        System.out.println("Inserted new holdings for " + transaction.getPortfolioId() + "/"
                                + transaction.getStockTicker() + " = " + delta + " (rows inserted: " + insRows + ")");
                    }
                } else {
                    System.out.println("Ignoring sell with no existing holdings for " + transaction.getPortfolioId()
                            + "/" + transaction.getStockTicker());
                }
            }
        } catch (SQLException exception) {
            System.out.println("Holdings update error: " + exception.getMessage());
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
        } catch (SQLException ex) {
            System.out.println("Error fetching user symbols: " + ex.getMessage());
        }

        return new ArrayList<>(symbols);
    }

    @Override
    public int getCurrentHoldings(String portfolioId, String ticker) {
        final String sqlHoldings = """
                    SELECT COALESCE(quantity, 0) AS qty
                    FROM holdings
                    WHERE portfolio_id = ? AND UPPER(ticker) = UPPER(?)
                """;
        final String sqlTxnFallback = """
                    SELECT COALESCE(SUM(CASE WHEN price > 0 THEN amount ELSE -amount END), 0) AS qty
                    FROM transactions
                    WHERE portfolio_id = ? AND UPPER(stock_name) = UPPER(?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sqlHoldings)) {
            try {
                ps.setInt(1, Integer.parseInt(portfolioId.trim()));
            } catch (NumberFormatException nfe) {
                ps.setString(1, portfolioId);
            }
            ps.setString(2, ticker == null ? null : ticker.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int qty = rs.getInt("qty");
                    if (qty != 0)
                        return qty;
                }
            }
        } catch (Exception ex) {
            // ignore and try fallback
        }

        try (PreparedStatement ps = connection.prepareStatement(sqlTxnFallback)) {
            try {
                ps.setInt(1, Integer.parseInt(portfolioId.trim()));
            } catch (NumberFormatException nfe) {
                ps.setString(1, portfolioId);
            }
            ps.setString(2, ticker == null ? null : ticker.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("qty") : 0;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * Test-only cleanup: remove all rows for a portfolio/ticker.
     */
    public void hardDeleteAllFor(String portfolioId, String stockName) throws SQLException {
        // Delete from transactions table
        final String sqlTransactions = "DELETE FROM transactions WHERE portfolio_id = ? AND stock_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlTransactions)) {
            ps.setString(1, portfolioId);
            ps.setString(2, stockName);
            ps.executeUpdate();
        }

        // Delete from holdings table
        final String sqlHoldings = "DELETE FROM holdings WHERE portfolio_id = ? AND UPPER(ticker) = UPPER(?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlHoldings)) {
            try {
                ps.setInt(1, Integer.parseInt(portfolioId.trim()));
            } catch (NumberFormatException nfe) {
                ps.setString(1, portfolioId);
            }
            ps.setString(2, stockName);
            ps.executeUpdate();
        }

    }

    @Override
    public double getPortfolioBalance(String portfolioId) {
        return DBPortfoliosDataAccessObject.fetchBalance(portfolioId);
    }

    @Override
    public void updatePortfolioBalance(String portfolioId, double newBalance) {
        DBPortfoliosDataAccessObject.updateBalance(portfolioId, newBalance);
    }
}
