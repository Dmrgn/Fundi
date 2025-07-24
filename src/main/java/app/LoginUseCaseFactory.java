package app;

import javax.swing.JOptionPane;

import java.io.IOException;

import entity.UserFactory;
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
import view.LoginView;

public class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {
    }

    public static LoginView create(
            ViewManagerModel viewManagerModel,
            MainViewModel mainViewModel,
            LoginViewModel loginViewModel,
            SignupViewModel signUpViewModel,
            UserDataAccessInterface userDataAccessObject) {

        try {
            LoginController loginController = createLoginUseCase(viewManagerModel, mainViewModel, loginViewModel,
                    signUpViewModel,
                    userDataAccessObject);
            return new LoginView(loginViewModel, loginController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static LoginController createLoginUseCase(
            ViewManagerModel viewManagerModel,
            MainViewModel mainViewModel,
            LoginViewModel loginViewModel,
            SignupViewModel signUpViewModel,
            UserDataAccessInterface userDataAccessObject) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, mainViewModel, loginViewModel,
                signUpViewModel);

        LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        return new LoginController(loginInteractor);
    }
}
