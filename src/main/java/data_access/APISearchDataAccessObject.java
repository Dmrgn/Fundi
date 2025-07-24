package data_access;

import entity.SearchResult;
import use_case.search.SearchDataAccessInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * API-based implementation of SearchDataAccessInterface using Alpha Vantage API.
 */
public class APISearchDataAccessObject implements SearchDataAccessInterface {

    private static final String API_BASE_URL = "https://www.alphavantage.co/query";
    private static final String FUNCTION = "SYMBOL_SEARCH";
    private final OkHttpClient httpClient;
    private final String apiKey;

    public APISearchDataAccessObject() throws IOException {
        this.httpClient = new OkHttpClient();
        // Read API key from file
        this.apiKey = Files.readString(Path.of("data/alpha_key.txt")).trim();
    }

    @Override
    public List<SearchResult> search(String query) throws Exception {
        final String url = String.format("%s?function=%s&keywords=%s&apikey=%s", 
                                        API_BASE_URL, FUNCTION, query, apiKey);

        final Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("API request failed: " + response.code());
            }

            final String responseBody = response.body().string();
            return parseSearchResults(responseBody);
        }
    }

    private List<SearchResult> parseSearchResults(String responseBody) throws Exception {
        final List<SearchResult> results = new ArrayList<>();
        final JSONObject jsonResponse = new JSONObject(responseBody);

        // Check for API error messages
        if (jsonResponse.has("Error Message")) {
            throw new Exception("API Error: " + jsonResponse.getString("Error Message"));
        }

        if (jsonResponse.has("Note")) {
            throw new Exception("API Rate Limit: " + jsonResponse.getString("Note"));
        }

        // Parse the best matches
        if (jsonResponse.has("bestMatches")) {
            final JSONArray bestMatches = jsonResponse.getJSONArray("bestMatches");
            
            for (int i = 0; i < bestMatches.length(); i++) {
                final JSONObject match = bestMatches.getJSONObject(i);
                
                final String symbol = match.getString("1. symbol");
                final String name = match.getString("2. name");
                final String type = match.getString("3. type");
                final String region = match.getString("4. region");
                final String marketOpen = match.getString("5. marketOpen");
                final String marketClose = match.getString("6. marketClose");
                final String timezone = match.getString("7. timezone");
                final String currency = match.getString("8. currency");
                final double matchScore = match.getDouble("9. matchScore");

                final SearchResult result = new SearchResult(symbol, name, type, region,
                                                           marketOpen, marketClose, timezone,
                                                           currency, matchScore);
                results.add(result);
            }
        }

        return results;
    }
}
