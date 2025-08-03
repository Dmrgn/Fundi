package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Presenter for the login use case
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final SignupViewModel signUpViewModel;
    private final MainViewModel mainViewModel;
    private ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
            MainViewModel mainViewModel,
            LoginViewModel loginViewModel,
            SignupViewModel signUpViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainViewModel = mainViewModel;
        this.loginViewModel = loginViewModel;
        this.signUpViewModel = signUpViewModel;
    }

    /**
     * Prepare the success view
     * @param response the output data
     */
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the tabbed main view.
        mainViewModel.getState().setUsername(response.getUsername());
        mainViewModel.firePropertyChanged();

        viewManagerModel.setState("tabbedmain");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view
     * @param error the explanation of the failure
     */
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setUsernameError(error);
        loginViewModel.firePropertyChanged();
    }

    /**
     * Switch to the signup view
     */
    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signUpViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
