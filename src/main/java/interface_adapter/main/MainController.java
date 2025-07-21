package interface_adapter.main;

import use_case.main.MainInputBoundary;
import use_case.main.MainInputData;

/**
 * Controller for the Main Use Case.
 */
public class MainController {

    private final MainInputBoundary mainUseCaseInteractor;

    public MainController(MainInputBoundary mainUseCaseInteractor) {
        this.mainUseCaseInteractor = mainUseCaseInteractor;
    }

    /**
     * Executes the Main Use Case.
     * 
     * @param username  the current user
     */
    public void execute(String username, String useCase) {
        final MainInputData mainInputData = new MainInputData(username, useCase);
        mainUseCaseInteractor.execute(mainInputData);
    }
}