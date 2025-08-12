package usecase.search;

import entity.SearchResult;
import java.util.List;

/**
 * The Search Interactor.
 */
public class GetMatches implements SearchInputBoundary {
    
    private final SearchDataAccessInterface searchDataAccess;
    private final SearchOutputBoundary searchPresenter;

    public GetMatches(SearchDataAccessInterface searchDataAccess, SearchOutputBoundary searchPresenter) {
        this.searchDataAccess = searchDataAccess;
        this.searchPresenter = searchPresenter;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        try {
            final String query = searchInputData.getQuery();
            
            if (query == null || query.trim().isEmpty()) {
                searchPresenter.prepareFailView("Search query cannot be empty.");
                return;
            }

            final List<SearchResult> searchResults = searchDataAccess.search(query.trim());
            final SearchOutputData searchOutputData = new SearchOutputData(searchResults, query, false);
            searchPresenter.prepareSuccessView(searchOutputData);
            
        } catch (Exception e) {
            searchPresenter.prepareFailView("Search failed: " + e.getMessage());
        }
    }
}
