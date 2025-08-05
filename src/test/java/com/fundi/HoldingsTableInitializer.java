package com.fundi;

import java.sql.*;
import java.util.Set;
import java.util.HashSet;

public class HoldingsTableInitializer {
    private static final String DB_URL = "jdbc:sqlite:data/fundi.sqlite";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // Create holdings table if not exists
            String createTable = """
                    CREATE TABLE IF NOT EXISTS holdings (
                        portfolio_id INTEGER NOT NULL,
                        ticker TEXT NOT NULL,
                        quantity INTEGER NOT NULL DEFAULT 0,
                        PRIMARY KEY (portfolio_id, ticker)
                    );
                    """;
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTable);
            }

            // Get valid portfolio ids
            Set<String> validPortfolios = new java.util.HashSet<>();
            try (Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT id FROM portfolios")) {
                while (rs.next()) {
                    validPortfolios.add(rs.getString("id"));
                }
            }

            // Aggregate holdings from transactions
            String query = """
                    SELECT portfolio_id, stock_name,
                        SUM(CASE WHEN price > 0 THEN amount ELSE -amount END) AS total_quantity
                    FROM transactions
                    GROUP BY portfolio_id, stock_name
                    """;
            try (Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {
                String insert = """
                        INSERT INTO holdings (portfolio_id, ticker, quantity)
                        VALUES (?, ?, ?)
                        ON CONFLICT(portfolio_id, ticker) DO UPDATE SET quantity = excluded.quantity
                        """;
                try (PreparedStatement pstmt = connection.prepareStatement(insert)) {
                    while (rs.next()) {
                        String portfolioId = rs.getString("portfolio_id");
                        String ticker = rs.getString("stock_name");
                        int quantity = rs.getInt("total_quantity");
                        if (quantity > 0 && validPortfolios.contains(portfolioId)) {
                            pstmt.setString(1, portfolioId);
                            pstmt.setString(2, ticker);
                            pstmt.setInt(3, quantity);
                            pstmt.executeUpdate();
                        }
                    }
                }
            }

            // Delete any holdings with quantity <= 0 (cleanup)
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("DELETE FROM holdings WHERE quantity <= 0");
            }

            System.out.println("Holdings table initialized from transactions.");
        }
    }
}
