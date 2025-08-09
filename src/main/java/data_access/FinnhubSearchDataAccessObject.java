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
 * FinnHub-based implementation of SearchDataAccessInterface using FinnHub
 * Symbol Lookup API.
 */
public class FinnhubSearchDataAccessObject implements SearchDataAccessInterface {

    private static final String API_BASE_URL = "https://finnhub.io/api/v1/search";
    private final OkHttpClient httpClient;
    private final String apiKey;

    public FinnhubSearchDataAccessObject() throws IOException {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        // Read API key from FHkey.txt
        this.apiKey = Files.readString(Path.of("data/FHkey.txt")).trim();
    }

    @Override
    public List<SearchResult> search(String query) throws Exception {
        // URL encode the query to handle spaces and special characters
        final String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
        final String url = String.format("%s?q=%s&token=%s", API_BASE_URL, encodedQuery, apiKey);

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
        if (jsonResponse.has("error")) {
            throw new Exception("FinnHub API Error: " + jsonResponse.getString("error"));
        }

        // Parse the search results from FinnHub
        if (jsonResponse.has("result")) {
            final JSONArray searchResults = jsonResponse.getJSONArray("result");

            for (int i = 0; i < searchResults.length(); i++) {
                final JSONObject result = searchResults.getJSONObject(i);

                final String symbol = result.getString("symbol");
                final String description = result.optString("description", "");
                final String type = result.optString("type", "");

                // Filter out any result with a period in symbol or description
                if (symbol.contains(".") || description.contains(".")) {
                    continue;
                }

                // FinnHub doesn't provide all the same fields as Alpha Vantage,
                // so we'll use default values for missing fields
                final SearchResult searchResult = new SearchResult(
                        symbol, // symbol
                        description, // name (using description)
                        type, // type
                        "US", // region (default to US)
                        "09:30", // marketOpen (default US market hours)
                        "16:00", // marketClose (default US market hours)
                        "America/New_York", // timezone (default US timezone)
                        "USD", // currency (default to USD)
                        1.0 // matchScore (default to perfect match)
                );
                results.add(searchResult);

                // Stop once we have exactly 10 valid results
                if (results.size() == 10) {
                    break;
                }
            }
        }

        return results;
    }
}
