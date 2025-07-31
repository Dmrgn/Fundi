package interface_adapter.search;

import entity.SearchResult;
import java.util.List;

/**
 * The State information representing the logged-in user's search results.
 */
public class SearchState {

    private List<SearchResult> searchResults;
    private String query = "";
    private String searchError;
    private boolean isLoading = false;

    public SearchState(SearchState copy) {
        this.searchResults = copy.searchResults;
        this.query = copy.query;
        this.searchError = copy.searchError;
        this.isLoading = copy.isLoading;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public SearchState() {
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSearchError() {
        return searchError;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
