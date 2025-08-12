package dataaccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import entity.StockData;
import usecase.analysis.AnalysisStockDataAccessInterface;
import usecase.buy.BuyStockDataAccessInterface;
import usecase.portfolio.PortfolioStockDataAccessInterface;
import usecase.recommend.RecommendStockDataAccessInterface;
import usecase.sell.SellStockDataAccessInterface;

/**
 * DAO for stock data implemented using a Database to persist data.
 */
public class DBStockDataAccessObject implements RecommendStockDataAccessInterface, BuyStockDataAccessInterface,
        SellStockDataAccessInterface, AnalysisStockDataAccessInterface, PortfolioStockDataAccessInterface {
    private static final Set<String> TICKERS = Set.of("AAPL", "MSFT", "AMZN", "NVDA", "GOOGL", "META",
            "BRK-B", "TSLA", "LLY", "UNH", "JPM", "V");
    private static final int NUM_DAYS = 10;
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final Map<String, List<StockData>> stocks = new HashMap<>();

    /**
     * Load the stock data into memory from SQL database.
     * @throws SQLException If SQL connection fails
     */
    public DBStockDataAccessObject() throws SQLException {
        LocalDate today = LocalDate.now();
        List<LocalDate> days = mostRecentnTradingDays(today);

        for (String ticker : TICKERS) {
            List<StockData> stockData = getStockData(ticker);
            if (!coversDates(stockData, days)) {
                try {
                    AlphaVantageClient client = new AlphaVantageClient();
                    List<StockData> fetched = client.fetch(ticker, NUM_DAYS);
                    saveAll(fetched);
                    stockData = getStockData(ticker);

                }

                catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
            List<StockData> cleaned = stockData.stream().filter(data -> days.contains(data.getTimestamp()))
                    .sorted(Comparator.comparing(StockData::getTimestamp).reversed())
                    .toList();
            stocks.put(ticker, cleaned);
        }
    }

    /**
     * Return the current price of a given ticker.
     * @param ticker The ticker to get the price of
     * @return The price of the ticker on the most recent trading day
     */
    @Override
    public double getPrice(String ticker) {
        return stocks.get(ticker).get(0).getPrice();
    }

    /**
     * Check whether the given ticker is tracked in this DAO.
     * @param ticker The ticker name
     * @return True or false if the ticker is valid
     */
    @Override
    public boolean hasTicker(String ticker) {
        return TICKERS.contains(ticker);
    }

    /**
     * Get the previous data for a given ticker.
     * @param ticker The ticker to get the previous data for
     * @return The stock data for the previous 10 trading days for the ticker
     */
    @Override
    public List<StockData> pastStockData(String ticker) {
        return stocks.get(ticker);
    }

    /**
     * Get the tickers supported by the DAO.
     * @return The tickers as a list
     */
    @Override
    public Set<String> getAvailableTickers() {
        return TICKERS;
    }

    private List<LocalDate> mostRecentnTradingDays(LocalDate today) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate mostRecent = today;
        while (dates.size() < NUM_DAYS) {
            mostRecent = mostRecent.minusDays(1);
            if (mostRecent.getDayOfWeek() != DayOfWeek.SATURDAY && mostRecent.getDayOfWeek() != DayOfWeek.SUNDAY ) {
                dates.add(mostRecent);
            }
        }
        return dates;
    }

    private boolean coversDates(List<StockData> stocksToCheck, List<LocalDate> dates) {
        Set<LocalDate> actual = stocksToCheck.stream().map(StockData::getTimestamp).collect(Collectors.toSet());
        return actual.containsAll(dates);
    }

    private List<StockData> getStockData(String ticker) {
        String query = """
             SELECT date, price FROM stocks
             WHERE name = ?
             ORDER BY date DESC
             LIMIT 10
            """;
        List<StockData> pastStocks = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, ticker);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                LocalDate timestamp = rs.getDate("date").toLocalDate();
                double price = rs.getDouble("price");
                pastStocks.add(new StockData(ticker, timestamp, price));
            }
        }

        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return pastStocks;
    }

    private void saveAll(List<StockData> stocksToSave) {
        for (StockData stock : stocksToSave) {
            String query = """
                    INSERT OR IGNORE INTO stocks (name, date, price)
                    VALUES(?, ?, ?)
                    """;
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, stock.getTicker());
                pstmt.setDate(2, java.sql.Date.valueOf(stock.getTimestamp()));
                pstmt.setDouble(3, stock.getPrice());
                pstmt.executeUpdate();
            }

            catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
