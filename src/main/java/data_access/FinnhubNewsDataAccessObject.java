package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import use_case.news.NewsAPIDataAccessInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class FinnhubNewsDataAccessObject implements NewsAPIDataAccessInterface {

    private final OkHttpClient client;
    private final String apiKey;

    public FinnhubNewsDataAccessObject() throws IOException {
        this.client = new OkHttpClient();
        this.apiKey = Files.readString(Path.of("data/FHkey.txt")).trim();
    }

    @Override
    public JSONArray fetchNewsForSymbol(String symbol, LocalDate fromDate, LocalDate toDate) throws IOException {
        String url = String.format(
            "https://finnhub.io/api/v1/company-news?symbol=%s&from=%s&to=%s&token=%s",
            symbol, fromDate, toDate, apiKey
        );

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // Return empty array on failure to prevent crashes
                return new JSONArray();
            }
            String jsonData = response.body().string();
            return new JSONArray(jsonData);
        }
    }
}
