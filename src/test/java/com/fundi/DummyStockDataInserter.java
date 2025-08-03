package com.fundi;

import java.sql.*;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class DummyStockDataInserter {
    private static final String DB_URL = "jdbc:sqlite:data/fundi.sqlite";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            insertDummyData(connection);
            System.out.println("Dummy stock data inserted successfully.");
        }
    }

    private static void insertDummyData(Connection connection) throws SQLException {
        String insertQuery = "INSERT OR IGNORE INTO stocks (name, date, price) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            Map<String, double[]> prices = Map.of(
                    "AAPL", new double[]{215.43, 213.97, 211.65, 212.50, 210.87, 209.32, 208.90, 207.75, 206.58, 205.41},
                    "MSFT", new double[]{365.80, 364.22, 362.77, 361.95, 360.10, 358.60, 357.15, 355.80, 353.45, 351.30},
                    "AMZN", new double[]{137.15, 136.42, 135.05, 133.88, 134.20, 132.74, 131.95, 130.60, 129.45, 128.90},
                    "NVDA", new double[]{1255.65, 1247.10, 1238.80, 1225.50, 1210.75, 1198.30, 1186.50, 1174.25, 1160.85, 1152.40}
            );

            List<LocalDate> dates = mostRecentNTradingDays(LocalDate.now());

            for (String ticker : prices.keySet()) {
                double[] tickerPrices = prices.get(ticker);
                for (int i = 0; i < dates.size(); i++) {
                    pstmt.setString(1, ticker);
                    pstmt.setDate(2, Date.valueOf(dates.get(i)));
                    pstmt.setDouble(3, tickerPrices[i]);
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
        }
    }

    private static List<LocalDate> mostRecentNTradingDays(LocalDate today) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate mostRecent = today;
        while (dates.size() < 10) {
            mostRecent = mostRecent.minusDays(1);
            if (mostRecent.getDayOfWeek() != DayOfWeek.SATURDAY && mostRecent.getDayOfWeek() != DayOfWeek.SUNDAY ) {
                dates.add(mostRecent);
            }
        }
        return dates;
    }
}
