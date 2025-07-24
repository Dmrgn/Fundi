package data_access;

import entity.StockData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlphaVantageClient {
    private final OkHttpClient client = new OkHttpClient();
    private static final String API_KEY = "LGT7I1WGL445BOPH";

    public List<StockData> fetch(String ticker, int numDays) throws IOException {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
                     + "&symbol=" + ticker + "&outputsize=compact&apikey=" + API_KEY;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error: " + response.code());
            }

            assert response.body() != null;
            String body = response.body().string();
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
            JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily)");

            List<StockData> stockDataList = new ArrayList<>();
            int i = 0;
            for (String dateString : timeSeries.keySet()) {
                if (i == numDays) return stockDataList;
                JSONObject entry = timeSeries.getJSONObject(dateString);
                LocalDate localDate = LocalDate.parse(dateString);
                double price = entry.getDouble("1. open");

                stockDataList.add(new StockData(ticker, localDate, price));
                i++;
            }
            return stockDataList;
        }
    }


}
