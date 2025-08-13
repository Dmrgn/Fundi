package dataaccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.SearchResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import usecase.search.SearchDataAccessInterface;

/**
 * FinnHub-based implementation of SearchDataAccessInterface using FinnHub
 * Symbol Lookup API.
 */
public class FinnhubSearchDataAccessObject implements SearchDataAccessInterface {

    private static final String API_BASE_URL = "https://finnhub.io/api/v1/search";
    private static final int CONNECTION_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 10;

    private final OkHttpClient httpClient;
    private final String apiKey;

    public FinnhubSearchDataAccessObject() throws IOException {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
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
            
            // Return empty list if no results
            if (searchResults.length() == 0) {
                return results;
            }

            for (int i = 0; i < searchResults.length(); i++) {
                final JSONObject result = searchResults.getJSONObject(i);

                final String symbol = result.optString("symbol", "");
                final String description = result.optString("description", "");
                final String type = result.optString("type", "Common Stock");

                // Only filter out clearly invalid symbols (empty or too long)
                if (symbol.trim().isEmpty() || symbol.length() > 10) {
                    continue;
                }

                // Use more realistic defaults - don't assume everything is US-based
                final SearchResult searchResult = new SearchResult(
                        symbol,
                        description.isEmpty() ? symbol : description,
                        type,
                        "Unknown",     // Don't assume region
                        "N/A",         // Don't assume market hours
                        "N/A",
                        "N/A",         // Don't assume timezone
                        "Unknown",     // Don't assume currency
                        0.8           // Use reasonable default match score
                );
                results.add(searchResult);
            }
        }

        return results;
    }
}
