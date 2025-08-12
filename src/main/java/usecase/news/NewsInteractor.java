package usecase.news;

import entity.SearchResult;
import org.json.JSONArray;
import org.json.JSONObject;
import usecase.portfolio.PortfolioTransactionDataAccessInterface;
import usecase.search.SearchDataAccessInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsInteractor implements NewsInputBoundary {
    private final NewsOutputBoundary newsPresenter;
    private final PortfolioTransactionDataAccessInterface portfolioDataAccess;
    private final SearchDataAccessInterface searchDataAccess;
    private final NewsAPIDataAccessInterface newsAPIDataAccess;

    // Default companies for users with no portfolio
    private static final List<String> DEFAULT_SYMBOLS = Arrays.asList("AAPL", "GOOGL", "MSFT", "TSLA");
    private static final int MAX_ARTICLES_PER_SYMBOL = 3;
    private static final int NEWS_FETCH_DAYS = 7;

    public NewsInteractor(NewsOutputBoundary newsPresenter,
                         PortfolioTransactionDataAccessInterface portfolioDataAccess,
                         SearchDataAccessInterface searchDataAccess,
                         NewsAPIDataAccessInterface newsAPIDataAccess) {
        this.newsPresenter = newsPresenter;
        this.portfolioDataAccess = portfolioDataAccess;
        this.searchDataAccess = searchDataAccess;
        this.newsAPIDataAccess = newsAPIDataAccess;
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
                newsPresenter.prepareView(new NewsOutputData(new String[][]{{"No Results", "Could not find a stock matching '" + input + "'.", ""}}));
                return;
            }

            List<String[]> allNews = new ArrayList<>();
            LocalDate today = LocalDate.now();
            LocalDate fromDate = today.minusDays(NEWS_FETCH_DAYS); // Fetch news from the last 7 days

            for (String symbol : symbolsToFetch) {
                JSONArray articles = newsAPIDataAccess.fetchNewsForSymbol(symbol, fromDate, today);

                if (articles.length() > 0) {
                    allNews.add(new String[]{"Latest News for " + symbol, "-----------------------------------", ""});
                }
                // Limit to 3 articles per symbol
                for (int i = 0; i < Math.min(MAX_ARTICLES_PER_SYMBOL, articles.length()); i++) {
                    JSONObject article = articles.getJSONObject(i);
                    allNews.add(new String[]{
                        article.optString("headline", "No Title"),
                        article.optString("summary", "No Summary Available."),
                        article.optString("url", "")
                    });
                }
            }

            if (allNews.isEmpty()) {
                allNews.add(new String[]{"No recent news found for your query.", "Please try another stock.", ""});
            }

            NewsOutputData outputData = new NewsOutputData(allNews.toArray(new String[0][]));
            newsPresenter.prepareView(outputData);

        } catch (Exception e) {
            e.printStackTrace();
            // Prepare an error view
            newsPresenter.prepareView(new NewsOutputData(new String[][]{{"Error", "Could not fetch news: " + e.getMessage(), ""}}));
        }
    }

    /**
     * This method is for testing purposes only. It allows manual injection of symbols
     * to fetch news for, bypassing the portfolio and search functionality.
     */
    public void fetchNewsForTesting(List<String> testSymbols) {
        try {
            List<String[]> allNews = new ArrayList<>();
            LocalDate today = LocalDate.now();
            LocalDate fromDate = today.minusDays(NEWS_FETCH_DAYS); // Fetch news from the last 7 days

            for (String symbol : testSymbols) {
                JSONArray articles = newsAPIDataAccess.fetchNewsForSymbol(symbol, fromDate, today);

                if (articles.length() > 0) {
                    allNews.add(new String[]{"Latest News for " + symbol, "-----------------------------------", ""});
                }
                // Limit to 3 articles per symbol
                for (int i = 0; i < Math.min(MAX_ARTICLES_PER_SYMBOL, articles.length()); i++) {
                    JSONObject article = articles.getJSONObject(i);
                    allNews.add(new String[]{
                        article.optString("headline", "No Title"),
                        article.optString("summary", "No Summary Available."),
                        article.optString("url", "")
                    });
                }
            }

            if (allNews.isEmpty()) {
                allNews.add(new String[]{"No recent news found for the provided symbols.", "Please try different symbols.", ""});
            }

            NewsOutputData outputData = new NewsOutputData(allNews.toArray(new String[0][]));
            newsPresenter.prepareView(outputData);

        } catch (Exception e) {
            e.printStackTrace();
            newsPresenter.prepareView(new NewsOutputData(new String[][]{{"Error", "Could not fetch news: " + e.getMessage(), ""}}));
        }
    }
}