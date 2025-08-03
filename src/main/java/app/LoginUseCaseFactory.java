package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.UserDataAccessInterface;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;

/**
 * Factory for the Login Use Case
 */
public class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {
    }

    public static LoginController create(
            ViewManagerModel viewManagerModel,
            MainViewModel  mainViewModel,
            LoginViewModel loginViewModel,
            SignupViewModel signupViewModel,
            UserDataAccessInterface dataAccessObject
    ) {
        LoginOutputBoundary loginPresenter = new LoginPresenter(
                viewManagerModel,
                mainViewModel,
                loginViewModel,
                signupViewModel
        );
        LoginInputBoundary loginInteractor = new LoginInteractor(dataAccessObject, loginPresenter);
        return new LoginController(loginInteractor);
    }
}
