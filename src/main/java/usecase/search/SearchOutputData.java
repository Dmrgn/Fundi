package usecase.search;

import entity.SearchResult;
import java.util.List;

/**
 * The Output Data for the Search Use Case.
 */
public class SearchOutputData {

    private final List<SearchResult> searchResults;
    private final String query;
    private final boolean useCaseFailed;

    public SearchOutputData(List<SearchResult> searchResults, String query, boolean useCaseFailed) {
        this.searchResults = searchResults;
        this.query = query;
        this.useCaseFailed = useCaseFailed;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public String getQuery() {
        return query;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
