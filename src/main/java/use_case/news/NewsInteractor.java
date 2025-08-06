package use_case.news;

import entity.SearchResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.portfolio.PortfolioTransactionDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsInteractor implements NewsInputBoundary {
    private final NewsOutputBoundary newsPresenter;
    private final PortfolioTransactionDataAccessInterface portfolioDataAccess;
    private final SearchDataAccessInterface searchDataAccess; // Added
    private final String apiKey;
    private final OkHttpClient client;

    // Default companies for users with no portfolio
    private static final List<String> DEFAULT_SYMBOLS = Arrays.asList("AAPL", "GOOGL", "MSFT", "TSLA");

    public NewsInteractor(NewsOutputBoundary newsPresenter,
                         PortfolioTransactionDataAccessInterface portfolioDataAccess,
                         SearchDataAccessInterface searchDataAccess) throws IOException { // Modified constructor
        this.newsPresenter = newsPresenter;
        this.portfolioDataAccess = portfolioDataAccess;
        this.searchDataAccess = searchDataAccess; // Added
        this.client = new OkHttpClient();
        // Read Finnhub API key from the correct file
        this.apiKey = Files.readString(Path.of("data/FHkey.txt")).trim();
    }

    @Override
    public void execute(NewsInputData inputData) {
        try {
            String input = inputData.getInput();
            List<String> symbolsToFetch = new ArrayList<>();

            if (inputData.isSearch()) {
                // It's a search query. Use the search DAO to find the symbol.
                List<SearchResult> searchResults = searchDataAccess.search(input);
                if (!searchResults.isEmpty()) {
                    // Use the symbol from the top search result.
                    symbolsToFetch.add(searchResults.get(0).getSymbol());
                }
            } else {
                // It's a username for portfolio news.
                symbolsToFetch = portfolioDataAccess.getUserSymbols(input);
                if (symbolsToFetch.isEmpty()) {
                    symbolsToFetch = DEFAULT_SYMBOLS;
                }
            }

            if (symbolsToFetch.isEmpty()) {
                newsPresenter.prepareView(new NewsOutputData(new String[][]{{"No Results", "Could not find a stock matching '" + input + "'."}}));
                return;
            }

            List<String[]> allNews = new ArrayList<>();
            LocalDate today = LocalDate.now();
            LocalDate fromDate = today.minusDays(7); // Fetch news from the last 7 days

            for (String symbol : symbolsToFetch) {
                String url = String.format(
                    "https://finnhub.io/api/v1/company-news?symbol=%s&from=%s&to=%s&token=%s",
                    symbol, fromDate, today, apiKey
                );

                Request request = new Request.Builder().url(url).build();
                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) continue;

                    String jsonData = response.body().string();
                    JSONArray articles = new JSONArray(jsonData);

                    if (articles.length() > 0) {
                        allNews.add(new String[]{"Latest News for " + symbol, "-----------------------------------"});
                    }

                    // Limit to 3 articles per symbol
                    for (int i = 0; i < Math.min(3, articles.length()); i++) {
                        JSONObject article = articles.getJSONObject(i);
                        allNews.add(new String[]{
                            article.optString("headline", "No Title"),
                            article.optString("summary", "No Summary Available.")
                        });
                    }
                    if (articles.length() > 0) {
                        allNews.add(new String[]{"", ""}); // Add spacing
                    }
                }
            }

            if (allNews.isEmpty()) {
                allNews.add(new String[]{"No recent news found for your query.", "Please try another stock."});
            }

            NewsOutputData outputData = new NewsOutputData(allNews.toArray(new String[0][]));
            newsPresenter.prepareView(outputData);

        } catch (Exception e) {
            e.printStackTrace();
            // Prepare an error view
            newsPresenter.prepareView(new NewsOutputData(new String[][]{{"Error", "Could not fetch news: " + e.getMessage()}}));
        }
    }

    /**
     * Simple check to differentiate a search query (like "AAPL") from a username.
     * THIS METHOD IS NO LONGER USED but can be kept for reference or removed.
     */
    private boolean isSearchQuery(String input) {
        // A short, non-spaced, uppercase string is likely a stock symbol.
        return input != null && !input.isEmpty() && input.matches("^[A-Z]{1,5}$");
    }
}