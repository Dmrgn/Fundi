package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;

/**
 * Factory for the Login Use Case.
 */
public final class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {

    }

    /**
     * Create the Login Controller.
     * @param viewManagerModel The View Manager Model
     * @param mainViewModel The Main View Model
     * @param loginViewModel The Login View Model
     * @param signupViewModel The Signup View Model
     * @param dataAccessObject The DAO
     * @return The Login Controller
     */
    public static LoginController create(
            ViewManagerModel viewManagerModel,
            MainViewModel mainViewModel,
            LoginViewModel loginViewModel,
            SignupViewModel signupViewModel,
            LoginUserDataAccessInterface dataAccessObject
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
