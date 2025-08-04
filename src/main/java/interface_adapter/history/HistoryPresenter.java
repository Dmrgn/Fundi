package interface_adapter.history;

import interface_adapter.ViewManagerModel;
import interface_adapter.navigation.NavigationController;
import use_case.history.HistoryOutputBoundary;
import use_case.history.HistoryOutputData;

/**
 * The presenter for the History Use Case.
 */
public class HistoryPresenter implements HistoryOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final HistoryViewModel historyViewModel;
    private final NavigationController navigationController;

    public HistoryPresenter(ViewManagerModel viewManagerModel, HistoryViewModel historyViewModel, NavigationController navigationController) {
        this.viewManagerModel = viewManagerModel;
        this.historyViewModel = historyViewModel;
        this.navigationController = navigationController;
    }

    /**
     * Prepare the view.
     * @param historyOutputData The portfolio history
     */
    @Override
    public void prepareView(HistoryOutputData historyOutputData) {
        HistoryState historyState = historyViewModel.getState();
        historyState.setTickers(historyOutputData.getTickers());
        historyState.setPrices(historyOutputData.getPrices());
        historyState.setAmounts(historyOutputData.getAmounts());
        historyState.setDates(historyOutputData.getDates());
        historyViewModel.setState(historyState);
        navigationController.navigateTo(viewManagerModel.getState(), "history");
        historyViewModel.firePropertyChanged();
        viewManagerModel.setState(historyViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Switch to the portfolio view.
     */
    @Override
    public void routeToPortfolio() {
        viewManagerModel.setState("portfolio");
        viewManagerModel.firePropertyChanged();
    }
}
