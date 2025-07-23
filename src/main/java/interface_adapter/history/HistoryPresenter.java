package interface_adapter.history;

import interface_adapter.ViewManagerModel;
import interface_adapter.portfolio.PortfolioViewModel;
import use_case.history.HistoryOutputBoundary;
import use_case.history.HistoryOutputData;

public class HistoryPresenter implements HistoryOutputBoundary {
    ViewManagerModel viewManagerModel;
    HistoryViewModel historyViewModel;

    public HistoryPresenter(ViewManagerModel viewManagerModel, HistoryViewModel historyViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.historyViewModel = historyViewModel;
    }

    @Override
    public void prepareView(HistoryOutputData historyOutputData) {
        HistoryState historyState = historyViewModel.getState();
        historyState.setTickers(historyOutputData.getTickers());
        historyState.setPrices(historyOutputData.getPrices());
        historyState.setAmounts(historyOutputData.getAmounts());
        historyState.setDates(historyOutputData.getDates());
        this.historyViewModel.setState(historyState);
        this.historyViewModel.firePropertyChanged();
        this.viewManagerModel.setState(historyViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
