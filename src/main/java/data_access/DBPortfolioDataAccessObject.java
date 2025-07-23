package data_access;

import entity.Portfolio;
import entity.Transaction;
import use_case.portfolio.PortfolioDataAccessInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class DBPortfolioDataAccessObject implements PortfolioDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, Portfolio> portfolios = new HashMap<>();

    public DBPortfolioDataAccessObject() throws SQLException {
        String query = """
            SELECT s.name AS stock_name,
                   t.amount AS stock_amount,
                   s.price as stock_price,
                   t.date AS stock_date,
                   t.portfolio_id AS portfolio_id
            FROM transactions t
            JOIN stocks s ON t.stock_name = s.name
        """;
        Portfolio portfolio = new Portfolio();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String portfolioId = rs.getString("portfolio_id");
                String stockName = rs.getString("name");
                double stockPrice = rs.getDouble("price");
                int amount = rs.getInt("amount");
                LocalDate date = rs.getDate("date").toLocalDate();
                if (portfolios.containsKey(portfolioId)) {
                    portfolio = portfolios.get(portfolioId);
                    portfolio.addTransaction(new Transaction(stockName, amount, date, stockPrice));
                } else {
                    portfolio = new Portfolio();
                    portfolio.addTransaction(new Transaction(stockName, amount, date, stockPrice));
                    portfolios.put(portfolioId, portfolio);
                }
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    /**
     * Get the portfolio data
     * @param portfolioId the id to search at
     * @return A portfolio object
     */
    @Override
    public Portfolio getPortfolio(String portfolioId) {
        return portfolios.get(portfolioId);
    }
}
