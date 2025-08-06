package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.history.HistoryController;
import interface_adapter.history.HistoryPresenter;
import interface_adapter.history.HistoryViewModel;
import interface_adapter.navigation.NavigationController;
import use_case.history.HistoryDataAccessInterface;
import use_case.history.HistoryInputBoundary;
import use_case.history.HistoryInteractor;
import use_case.history.HistoryOutputBoundary;

/**
 * Factory for the History Use Case.
 */
public final class HistoryUseCaseFactory {
    private HistoryUseCaseFactory() {

    }

    /**
     * Creates the History Controller.
     * @param viewManagerModel The View Manager Model
     * @param historyViewModel The History View Model
     * @param dataAccessObject The DAO
     * @param navigationController The Navigation Controller
     * @return The History Controller
     */
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
