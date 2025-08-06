package data_access;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import entity.StockData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A client class for the Alpha Vantage Stock API.
 */
public class AlphaVantageClient {
    private static final String API_KEY = "RPMO4OR4VEE0XJ8V";
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Return a list of stock data for a given ticker for a given number of days.
     * @param ticker The ticker to get the data for
     * @param numDays The number of days of stock price data needed
     * @return A List of stock data objects for the previous days
     * @throws IOException IOException is thrown if OKHTTP request fails
     */
    public List<StockData> fetch(String ticker, int numDays) throws IOException {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
                     + "&symbol=" + ticker + "&outputsize=compact&apikey=" + API_KEY;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error: " + response.code());
            }

            String body = null;
            if (response.body() != null) {
                body = response.body().string();
            }
            JSONObject timeSeries = getJsonObject(body);

            List<StockData> stockDataList = new ArrayList<>();
            List<String> sortedDates = new ArrayList<>(timeSeries.keySet());
            sortedDates.sort(Comparator.reverseOrder());
            int i = 0;
            for (String dateString : sortedDates) {
                if (i == numDays) {
                    return stockDataList;
                }
                JSONObject entry = timeSeries.getJSONObject(dateString);
                LocalDate localDate = LocalDate.parse(dateString);
                double price = entry.getDouble("1. open");

                stockDataList.add(new StockData(ticker, localDate, price));
                i++;
            }
            return stockDataList;
        }
    }

    private static JSONObject getJsonObject(String body) throws IOException {
        JSONObject jsonObject = new JSONObject(body);

        // Handle errors
        if (jsonObject.has("Error Message")) {
            throw new IOException("Alpha Vantage Error: " + jsonObject.getString("Error Message"));
        }
        if (jsonObject.has("Note")) {
            throw new IOException("Rate limit hit: " + jsonObject.getString("Note"));
        }

        if (jsonObject.has("Information")) {
            throw new IOException("Premium endpoint warning: " + jsonObject.getString("Information"));
        }

        if (!jsonObject.has("Time Series (Daily)")) {
            throw new IOException("Unexpected response: no 'Time Series (Daily)' found");
        }
        return jsonObject.getJSONObject("Time Series (Daily)");
    }


}
