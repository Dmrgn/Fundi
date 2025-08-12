package usecase.search;

/**
 * Input Boundary for the Search Use Case.
 */
public interface SearchInputBoundary {

    /**
     * Executes the Search Use Case.
     * @param searchInputData the input data for this use case
     */
    void execute(SearchInputData searchInputData);
}
