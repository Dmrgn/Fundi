package usecase.notifications;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

/**
 * Service to check for recent news for a given stock ticker
 */
public class NewsNotificationService {
    private final String apiKey;
    private final OkHttpClient client;

    public NewsNotificationService() throws IOException {
        this.apiKey = Files.readString(Path.of("data/FHkey.txt")).trim();
        this.client = new OkHttpClient();
    }

    /**
     * Check if there's recent news (last 3 days) for a given ticker
     * @param ticker The stock ticker to check
     * @return true if recent news exists, false otherwise
     */
    public boolean hasRecentNews(String ticker) {
        try {
            LocalDate today = LocalDate.now();
            LocalDate fromDate = today.minusDays(3);
            
            String url = String.format(
                "https://finnhub.io/api/v1/company-news?symbol=%s&from=%s&to=%s&token=%s",
                ticker, fromDate, today, apiKey
            );

            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return false;
                }

                String jsonData = response.body().string();
                JSONArray articles = new JSONArray(jsonData);
                
                // Return true if there are any recent articles
                return articles.length() > 0;
            }
        } catch (Exception e) {
            System.err.println("Error checking news for " + ticker + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Get a brief summary of recent news for a ticker
     * @param ticker The stock ticker
     * @return A brief news summary or null if no news
     */
    public String getNewsSummary(String ticker) {
        try {
            LocalDate today = LocalDate.now();
            LocalDate fromDate = today.minusDays(3);
            
            String url = String.format(
                "https://finnhub.io/api/v1/company-news?symbol=%s&from=%s&to=%s&token=%s",
                ticker, fromDate, today, apiKey
            );

            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return null;
                }

                String jsonData = response.body().string();
                JSONArray articles = new JSONArray(jsonData);
                
                if (articles.length() > 0) {
                    JSONObject firstArticle = articles.getJSONObject(0);
                    return firstArticle.optString("headline", "Recent news available for " + ticker);
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
