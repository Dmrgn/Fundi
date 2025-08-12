package interfaceadapter.search;

import usecase.search.SearchOutputBoundary;
import usecase.search.SearchOutputData;

/**
 * The Presenter for the Search Use Case.
 */
public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;

    public SearchPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData response) {
        // On success, update the search state with results
        final SearchState searchState = searchViewModel.getState();
        searchState.setSearchResults(response.getSearchResults());
        searchState.setQuery(response.getQuery());
        searchState.setSearchError(null);
        searchState.setLoading(false);
        searchViewModel.setState(searchState);
        searchViewModel.firePropertyChanged();

        // Don't change views - stay on main view but update search results
    }

    @Override
    public void prepareFailView(String error) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setSearchError(error);
        searchState.setSearchResults(null);
        searchState.setLoading(false);
        searchViewModel.setState(searchState);
        searchViewModel.firePropertyChanged();
    }
}
