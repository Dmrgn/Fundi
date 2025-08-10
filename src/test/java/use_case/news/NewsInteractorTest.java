package use_case.news;

import entity.SearchResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.portfolio.PortfolioTransactionDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import entity.Transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsInteractorTest {

    private NewsInteractor newsInteractor;
    private TestNewsPresenter testPresenter;
    private TestSearchDataAccess testSearchDataAccess;
    private TestNewsAPIDataAccess testNewsAPIDataAccess;

    @BeforeEach
    void setUp() {
        testPresenter = new TestNewsPresenter();
        testSearchDataAccess = new TestSearchDataAccess();
        testNewsAPIDataAccess = new TestNewsAPIDataAccess();
        TestPortfolioDataAccess testPortfolioDataAccess = new TestPortfolioDataAccess();
        
        newsInteractor = new NewsInteractor(testPresenter, testPortfolioDataAccess, testSearchDataAccess, testNewsAPIDataAccess);
    }

    @Test
    void testNewsSearchForAAPLTicker() {
        // Given
        String searchQuery = "AAPL";
        List<SearchResult> searchResults = List.of(
            new SearchResult("AAPL", "Apple Inc.", "Equity", "United States", "09:30", "16:00", "UTC-04", "USD", 1.0)
        );
        testSearchDataAccess.setSearchResults(searchResults);

        // Mock the news API response
        JSONArray articles = new JSONArray();
        articles.put(new JSONObject()
            .put("headline", "Big News for AAPL!")
            .put("summary", "Apple stock is up.")
            .put("url", "http://example.com/aapl"));
        testNewsAPIDataAccess.setArticles(articles);

        NewsInputData inputData = new NewsInputData(searchQuery, true);

        // When
        newsInteractor.execute(inputData);

        // Then
        assertTrue(testPresenter.isViewPrepared());
        assertNotNull(testPresenter.getOutputData());
        
        String[][] newsItems = testPresenter.getOutputData().getNewsItems();
        assertTrue(newsItems.length > 1, "Should have a header and at least one article");
        
        // Verify that news was found for AAPL
        boolean foundAAPLNews = false;
        for (String[] newsItem : newsItems) {
            if (newsItem[0].contains("AAPL")) {
                foundAAPLNews = true;
                break;
            }
        }
        assertTrue(foundAAPLNews, "Should find news for AAPL ticker");
        assertEquals("Big News for AAPL!", newsItems[1][0]);
    }

    // Test helper classes
    private static class TestNewsPresenter implements NewsOutputBoundary {
        private boolean viewPrepared = false;
        private NewsOutputData outputData;

        @Override
        public void prepareView(NewsOutputData newsOutputData) {
            this.viewPrepared = true;
            this.outputData = newsOutputData;
        }

        public boolean isViewPrepared() {
            return viewPrepared;
        }

        public NewsOutputData getOutputData() {
            return outputData;
        }
    }

    private static class TestPortfolioDataAccess implements PortfolioTransactionDataAccessInterface {
        private List<String> userSymbols = new ArrayList<>();

        public void setUserSymbols(List<String> symbols) {
            this.userSymbols = new ArrayList<>(symbols);
        }

        @Override
        public List<String> getUserSymbols(String username) {
            return new ArrayList<>(userSymbols);
        }

        @Override
        public List<Transaction> pastTransactions(String portfolioId) {
            return new ArrayList<>();
        }
    }

    private static class TestSearchDataAccess implements SearchDataAccessInterface {
        private List<SearchResult> searchResults = new ArrayList<>();
        private boolean shouldThrowException = false;

        public void setSearchResults(List<SearchResult> results) {
            this.searchResults = new ArrayList<>(results);
        }

        public void setShouldThrowException(boolean shouldThrow) {
            this.shouldThrowException = shouldThrow;
        }

        @Override
        public List<SearchResult> search(String query) throws Exception {
            if (shouldThrowException) {
                throw new Exception("Test exception");
            }
            return new ArrayList<>(searchResults);
        }
    }

    private static class TestNewsAPIDataAccess implements NewsAPIDataAccessInterface {
        private JSONArray articles = new JSONArray();

        public void setArticles(JSONArray articles) {
            this.articles = articles;
        }

        @Override
        public JSONArray fetchNewsForSymbol(String symbol, LocalDate fromDate, LocalDate toDate) throws IOException {
            return articles;
        }
    }
}
