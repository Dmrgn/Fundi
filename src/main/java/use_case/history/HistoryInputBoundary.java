package use_case.history;

/**
 * The Input Boundary for the History Use Case.
 */
public interface HistoryInputBoundary {
    /**
     * Execute History Use Case.
     * @param historyInputData the input data
     */
    void execute(HistoryInputData historyInputData);

    /**
     * Switch to the Portfolio View.
     */
    void routeToPortfolio();
}
