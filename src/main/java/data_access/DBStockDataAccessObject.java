package data_access;

import entity.StockData;
import use_case.analysis.AnalysisStockDataAccessInterface;
import use_case.buy.BuyStockDataAccessInterface;
import use_case.recommend.RecommendDataAccessInterface;
import use_case.sell.SellStockDataAccessInterface;

import java.io.IOException;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DBStockDataAccessObject implements RecommendDataAccessInterface, BuyStockDataAccessInterface,
        SellStockDataAccessInterface, AnalysisStockDataAccessInterface {
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
    private final AlphaVantageClient client = new AlphaVantageClient();
    private final Map<String, List<StockData>> stocks = new HashMap<>();
//    private static final String[] TICKERS = {
//            "AAPL", "MSFT", "AMZN", "NVDA", "GOOGL", "META", "BRK-B", "TSLA", "LLY", "UNH",
//            "JPM", "V", "JNJ", "PG", "HD", "MA", "XOM", "MRK", "AVGO", "COST",
//            "ABBV", "WMT", "ADBE", "PEP", "CVX", "KO", "CRM", "NFLX", "ACN", "ABT",
//            "MCD", "AMD", "TMO", "INTC", "LIN", "DHR", "ORCL", "TXN", "NEE", "AMGN",
//            "PFE", "QCOM", "NKE", "UPS", "MS", "MDT", "PM", "BA", "HON", "UNP"
//    };
    private static final Set<String> TICKERS = Set.of(new String[]{"AAPL", "MSFT", "AMZN", "NVDA"});
    private static final int NUM_DAYS = 10;

    public DBStockDataAccessObject() throws SQLException {
        LocalDate today = LocalDate.now();
        List<LocalDate> days = mostRecentNTradingDays(today);

        for (String ticker : TICKERS) {
            List<StockData> stockData = getStockData(ticker);
            if (!coversDates(stockData, days)) {
                try {
                    List<StockData> fetched = client.fetch(ticker, NUM_DAYS);
                    saveAll(fetched);
                    stockData = getStockData(ticker);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            List<StockData> cleaned = stockData.stream().filter(sd -> days.contains(sd.getTimestamp()))
                    .sorted(Comparator.comparing(StockData::getTimestamp).reversed())
                    .toList();
            stocks.put(ticker, cleaned);
        }


    }

    @Override
    public double getPrice(String ticker) {
        return stocks.get(ticker).get(0).getPrice();
    }

    @Override
    public boolean hasTicker(String ticker) {
        return TICKERS.contains(ticker);
    }

    @Override
    public List<StockData> pastStockData(String ticker) {
        return stocks.get(ticker);
    }

    @Override
    public String[] getAvailableTickers() {
        return TICKERS.toArray(new String[0]);
    }

    private List<LocalDate> mostRecentNTradingDays(LocalDate today) {
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

    private boolean coversDates(List<StockData> stocks, List<LocalDate> dates) {
        Set<LocalDate> actual = stocks.stream().map(StockData::getTimestamp).collect(Collectors.toSet());
        return actual.containsAll(dates);
    }

    private List<StockData> getStockData(String ticker) {
        String query = """
        SELECT date, price FROM stocks
                 WHERE name = ?
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pastStocks;
    }

    private void saveAll(List<StockData> stocks) {
        for (StockData stock : stocks) {
            String query = """
                    INSERT OR IGNORE INTO stocks (name, date, price)
                    VALUES(?, ?, ?)
                    """;
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, stock.getTicker());
                pstmt.setDate(2,java.sql.Date.valueOf(stock.getTimestamp()));
                pstmt.setDouble(3, stock.getPrice());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public List<StockData> getPastPrices(String ticker) {
        return stocks.get(ticker);
    }
}
