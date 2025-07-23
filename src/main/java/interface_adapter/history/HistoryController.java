package interface_adapter.history;

import use_case.history.HistoryInputBoundary;
import use_case.history.HistoryInputData;

public class HistoryController {
        private HistoryInputBoundary historyInputBoundary;

        public HistoryController(HistoryInputBoundary historyInputBoundary) {
            this.historyInputBoundary = historyInputBoundary;
        }

        public void execute(String portfolioId) {
            historyInputBoundary.execute(new HistoryInputData(portfolioId));
        }
}
