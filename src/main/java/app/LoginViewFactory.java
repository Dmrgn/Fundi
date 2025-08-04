package app;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import view.LoginView;

/**
 * Factory for the Login View.
 */
public final class LoginViewFactory {
    private LoginViewFactory() {

    }

    /**
     * Create the Login View.
     * @param loginViewModel The Login View Model
     * @param loginController The Login Controller
     * @return The Login View
     */
    public static LoginView create(LoginViewModel loginViewModel, LoginController loginController) {
        return new LoginView(loginViewModel, loginController);
    }
}
