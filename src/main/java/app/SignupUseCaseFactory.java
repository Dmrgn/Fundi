package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.login.LoginViewModel;
import interfaceadapter.signup.SignupController;
import interfaceadapter.signup.SignupPresenter;
import interfaceadapter.signup.SignupViewModel;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;
import usecase.signup.SignupUserDataAccessInterface;

/**
 * Factory for the Signup Use Case.
 */
public final class SignupUseCaseFactory {

    /** Prevent instantiation. */
    private SignupUseCaseFactory() {
    }

    /**
     * Create the Signup Controller.
     * @param viewManagerModel The View Manager Model
     * @param signupViewModel The Signup View Model
     * @param loginViewModel The Login View Model
     * @param dataAccessObject The DAO
     * @return The Signup Controller
     */
    public static SignupController create(
            ViewManagerModel viewManagerModel,
            SignupViewModel signupViewModel,
            LoginViewModel loginViewModel,
            SignupUserDataAccessInterface dataAccessObject
    ) {
        SignupOutputBoundary signupPresenter = new SignupPresenter(
                viewManagerModel,
                signupViewModel,
                loginViewModel
        );
        SignupInputBoundary signupInteractor = new SignupInteractor(
                dataAccessObject,
                signupPresenter
        );
        return new SignupController(signupInteractor);
    }

}
