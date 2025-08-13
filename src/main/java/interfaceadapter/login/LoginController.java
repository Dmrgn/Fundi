package interfaceadapter.login;

import usecase.login.LoginInputBoundary;
import usecase.login.LoginInputData;
import usecase.remember_me.RememberMeInteractor;

/**
 * The controller for the Login Use Case.
 */
public class LoginController {

    private final LoginInputBoundary loginUseCaseInteractor;
    private final RememberMeInteractor rememberMeInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor, RememberMeInteractor rememberMeInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
        this.rememberMeInteractor = rememberMeInteractor;
    }

    /**
     * Executes the Login Use Case.
     * 
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String username, String password) {
        LoginInputData loginInputData = new LoginInputData(username, password);
        loginUseCaseInteractor.execute(loginInputData);
    }

    public void switchToSignupView() {
        loginUseCaseInteractor.switchToSignupView();
    }

    public void saveRememberMe(String username, boolean rememberMe) {
        rememberMeInteractor.saveRememberMe(username, rememberMe);
    }
}
