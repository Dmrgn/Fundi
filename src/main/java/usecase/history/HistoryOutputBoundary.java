package usecase.history;

/**
 * Output Boundary for the History Use Case.
 */
public interface HistoryOutputBoundary {

    /**
     * Prepare the History View.
     * @param historyOutputData the output data
     */
    void prepareView(HistoryOutputData historyOutputData);

    /**
     * Switch to the Portfolio View.
     */
    void routeToPortfolio();
}
