package interfaceadapter.search;

import interfaceadapter.ViewModel;

/**
 * The View Model for the Search View.
 */
public class SearchViewModel extends ViewModel<SearchState> {

    public static final String TITLE_LABEL = "Search Results";
    public static final String SEARCH_BUTTON_LABEL = "Search";

    public SearchViewModel() {
        super("search");
        setState(new SearchState());
    }
}
