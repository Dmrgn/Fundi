package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;

public class SignupUseCaseFactory {

    /** Prevent instantiation. */
    private SignupUseCaseFactory() {
    }

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
