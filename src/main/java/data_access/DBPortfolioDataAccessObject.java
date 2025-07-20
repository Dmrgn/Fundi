package data_access;

import entity.Portfolio;
import entity.Stock;
import use_case.portfolio.PortfolioDataAccessInterface;

import java.sql.*;


public class DBPortfolioDataAccessObject implements PortfolioDataAccessInterface {
    final Connection connection;

    DBPortfolioDataAccessObject(Connection connection){
        this.connection = connection;
    }

    /**
     * Get the portfolio data
     * @param portfolioId the id to search at
     * @return A portfolio object
     */
    @Override
    public Portfolio getPortfolio(String portfolioId) {
        String query = """
            SELECT s.id AS stock_id,
                   s.name AS stock_name,
                   SUM(t.amount) AS stock_amount,
                   s.price as stock_price,
            FROM transactions t
            JOIN stocks s ON t.stock_id = s.id
            WHERE t.portfolio_id = ?
            GROUP BY s.id, s.name, s.price
        """;
        Portfolio portfolio = new Portfolio();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, portfolioId);
            ResultSet rs = pstmt.executeQuery(query);
            while (rs.next()) {
                String stockName = rs.getString("name");
                double stockPrice = rs.getDouble("price");
                int amount = rs.getInt("amount");
                portfolio.addStock(new Stock(stockName, stockPrice, amount));
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return portfolio;
    }
}
