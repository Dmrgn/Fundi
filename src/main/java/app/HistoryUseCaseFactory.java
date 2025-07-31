package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.history.HistoryController;
import use_case.history.HistoryInteractor;
import interface_adapter.history.HistoryPresenter;
import interface_adapter.history.HistoryViewModel;
import interface_adapter.navigation.NavigationController;
import use_case.history.HistoryDataAccessInterface;
import use_case.history.HistoryInputBoundary;
import use_case.history.HistoryOutputBoundary;

public class HistoryUseCaseFactory {
    private HistoryUseCaseFactory() {

    }

    public static HistoryController create(
            ViewManagerModel viewManagerModel,
            HistoryViewModel historyViewModel,
            HistoryDataAccessInterface dataAccessObject,
            NavigationController navigationController
    ) {
        HistoryOutputBoundary historyPresenter = new HistoryPresenter(viewManagerModel, historyViewModel, navigationController);
        HistoryInputBoundary historyInteractor = new HistoryInteractor(dataAccessObject, historyPresenter);
        return new HistoryController(historyInteractor);
    }
}
