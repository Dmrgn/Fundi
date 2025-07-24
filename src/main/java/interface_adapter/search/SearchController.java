package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * Controller for the Search Use Case.
 */
public class SearchController {

    private final SearchInputBoundary searchUseCaseInteractor;

    public SearchController(SearchInputBoundary searchUseCaseInteractor) {
        this.searchUseCaseInteractor = searchUseCaseInteractor;
    }

    /**
     * Executes the Search Use Case.
     * @param query the search query
     */
    public void execute(String query) {
        final SearchInputData searchInputData = new SearchInputData(query);
        searchUseCaseInteractor.execute(searchInputData);
    }
}
