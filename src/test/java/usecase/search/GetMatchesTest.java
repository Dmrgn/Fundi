package usecase.search;

import entity.SearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for the Search Use Case.
 */
public class GetMatchesTest {

    private GetMatches searchInteractor;
    private TestSearchDataAccess testDataAccess;
    private TestSearchPresenter testPresenter;

    @BeforeEach
    void setUp() {
        testDataAccess = new TestSearchDataAccess();
        testPresenter = new TestSearchPresenter();
        searchInteractor = new GetMatches(testDataAccess, testPresenter);
    }

    @Test
    void testSuccessfulSearch() {
        // Given
        String query = "AAPL";
        SearchResult result = new SearchResult("AAPL", "Apple Inc.", "Equity", "United States",
                "09:30", "16:00", "UTC-04", "USD", 1.0);
        testDataAccess.setSearchResult(List.of(result));

        // When
        SearchInputData inputData = new SearchInputData(query);
        searchInteractor.execute(inputData);

        // Then
        assertTrue(testPresenter.isSuccessCalled());
        assertFalse(testPresenter.isFailureCalled());
        assertEquals(query, testPresenter.getOutputData().getQuery());
        assertEquals(1, testPresenter.getOutputData().getSearchResults().size());
        assertEquals("AAPL", testPresenter.getOutputData().getSearchResults().get(0).getSymbol());
    }

    @Test
    void testEmptyQuery() {
        // When
        SearchInputData inputData = new SearchInputData("");
        searchInteractor.execute(inputData);

        // Then
        assertFalse(testPresenter.isSuccessCalled());
        assertTrue(testPresenter.isFailureCalled());
        assertEquals("Search query cannot be empty.", testPresenter.getErrorMessage());
    }

    @Test
    void testSearchFailure() {
        // Given
        testDataAccess.setThrowException(true);

        // When
        SearchInputData inputData = new SearchInputData("AAPL");
        searchInteractor.execute(inputData);

        // Then
        assertFalse(testPresenter.isSuccessCalled());
        assertTrue(testPresenter.isFailureCalled());
        assertTrue(testPresenter.getErrorMessage().contains("Search failed"));
    }

    // Test implementation classes
    private static class TestSearchDataAccess implements SearchDataAccessInterface {
        private List<SearchResult> searchResults = new ArrayList<>();
        private boolean throwException = false;

        public void setSearchResult(List<SearchResult> results) {
            this.searchResults = results;
        }

        public void setThrowException(boolean throwException) {
            this.throwException = throwException;
        }

        @Override
        public List<SearchResult> search(String query) throws Exception {
            if (throwException) {
                throw new Exception("API Error");
            }
            return searchResults;
        }
    }

    private static class TestSearchPresenter implements SearchOutputBoundary {
        private boolean successCalled = false;
        private boolean failureCalled = false;
        private SearchOutputData outputData;
        private String errorMessage;

        @Override
        public void prepareSuccessView(SearchOutputData outputData) {
            this.successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failureCalled = true;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccessCalled() { return successCalled; }
        public boolean isFailureCalled() { return failureCalled; }
        public SearchOutputData getOutputData() { return outputData; }
        public String getErrorMessage() { return errorMessage; }
    }
}
