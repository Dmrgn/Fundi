package interfaceadapter.signup;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.login.LoginState;
import interfaceadapter.login.LoginViewModel;
import usecase.signup.SignupOutputBoundary;
import usecase.signup.SignupOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
            SignupViewModel signupViewModel,
            LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    /**
     * Prepare the success view
     * @param response the output data
     */
    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to the login view.
        LoginState loginState = loginViewModel.getState();
        loginState.setUsername(response.getUsername());
        this.loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view
     * @param error the explanation of the failure
     */
    @Override
    public void prepareFailView(String error) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(error);
        signupViewModel.firePropertyChanged();
    }

    /**
     * Switch to the Login View
     */
    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
