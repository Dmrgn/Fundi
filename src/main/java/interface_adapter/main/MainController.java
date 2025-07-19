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
     * @param username  the username to sign up
     * @param password1 the password
     * @param password2 the password repeated
     */
    public void execute(String username) {
        final MainInputData mainInputData = new MainInputData(username);
        mainUseCaseInteractor.execute(mainInputData);
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToLoginView() {
//         mainUseCaseInteractor.switchToLoginView();
    }
}