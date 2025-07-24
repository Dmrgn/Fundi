package use_case.history;

public interface HistoryOutputBoundary {
    void prepareView(HistoryOutputData historyOutputData);
    void routeToPortfolio();
}
