package interfaceadapter.history;

import usecase.history.HistoryInputBoundary;
import usecase.history.HistoryInputData;

/**
 * The controller for the History Use Case.
 */
public class HistoryController {
    private final HistoryInputBoundary historyInputBoundary;

    public HistoryController(HistoryInputBoundary historyInputBoundary) {
        this.historyInputBoundary = historyInputBoundary;
    }

    /**
     * Execute the History Use Case.
     * @param portfolioId The id of the portfolio to view the history of
     */
    public void execute(String portfolioId) {
        historyInputBoundary.execute(new HistoryInputData(portfolioId));
    }

    /**
     * Switch to the Portfolio View.
     */
    public void routeToPortfolio() {
        historyInputBoundary.routeToPortfolio();
    }
}
