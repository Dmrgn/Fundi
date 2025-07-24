package use_case.search;

import entity.SearchResult;
import java.util.List;

/**
 * Data Access Interface for the Search Use Case.
 */
public interface SearchDataAccessInterface {

    /**
     * Search for stocks matching the given query.
     * @param query the search query
     * @return a list of search results
     * @throws Exception if the search fails
     */
    List<SearchResult> search(String query) throws Exception;
}
