package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.history.HistoryController;
import use_case.history.HistoryInteractor;
import interface_adapter.history.HistoryPresenter;
import interface_adapter.history.HistoryViewModel;
import use_case.history.HistoryDataAccessInterface;
import use_case.history.HistoryInputBoundary;
import use_case.history.HistoryOutputBoundary;

public class HistoryUseCaseFactory {
    private HistoryUseCaseFactory() {

    }

    public static HistoryController create(
            ViewManagerModel viewManagerModel,
            HistoryViewModel historyViewModel,
            HistoryDataAccessInterface dataAccessObject
    ) {
        HistoryOutputBoundary historyPresenter = new HistoryPresenter(viewManagerModel, historyViewModel);
        HistoryInputBoundary historyInteractor = new HistoryInteractor(dataAccessObject, historyPresenter);
        return new HistoryController(historyInteractor);
    }
}
