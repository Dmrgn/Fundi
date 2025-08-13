package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.login.LoginController;
import interfaceadapter.login.LoginPresenter;
import interfaceadapter.login.LoginViewModel;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.signup.SignupViewModel;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInteractor;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginUserDataAccessInterface;
import usecase.remember_me.RememberMeInteractor;
import usecase.remember_me.RememberMeUserDataAccessInterface;

/**
 * Factory for the Login Use Case.
 */
public final class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {

    }

    /**
     * Create the Login Controller.
     * 
     * @param viewManagerModel The View Manager Model
     * @param mainViewModel    The Main View Model
     * @param loginViewModel   The Login View Model
     * @param signupViewModel  The Signup View Model
     * @param dataAccessObject The DAO
     * @return The Login Controller
     */
    public static LoginController create(
            ViewManagerModel viewManagerModel,
            MainViewModel mainViewModel,
            LoginViewModel loginViewModel,
            SignupViewModel signupViewModel,
            LoginUserDataAccessInterface dataAccessObject) {
        LoginOutputBoundary loginPresenter = new LoginPresenter(
                viewManagerModel,
                mainViewModel,
                loginViewModel,
                signupViewModel);
        LoginInputBoundary loginInteractor = new LoginInteractor(dataAccessObject, loginPresenter);
        RememberMeInteractor rememberMeInteractor = new RememberMeInteractor(
                (RememberMeUserDataAccessInterface) dataAccessObject, loginInteractor);
        return new LoginController(loginInteractor, rememberMeInteractor);
    }
}
