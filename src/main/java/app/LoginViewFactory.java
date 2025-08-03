package app;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import view.LoginView;

/**
 * Factory for the Login View
 */
public class LoginViewFactory {
    private LoginViewFactory() {

    }

    public static LoginView create(LoginViewModel loginViewModel, LoginController loginController) {
        return new LoginView(loginViewModel, loginController);
    }
}
