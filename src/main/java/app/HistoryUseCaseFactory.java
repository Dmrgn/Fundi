package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.history.HistoryController;
import interfaceadapter.history.HistoryPresenter;
import interfaceadapter.history.HistoryViewModel;
import interfaceadapter.navigation.NavigationController;
import usecase.history.HistoryDataAccessInterface;
import usecase.history.HistoryInputBoundary;
import usecase.history.HistoryInteractor;
import usecase.history.HistoryOutputBoundary;

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
