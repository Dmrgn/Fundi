package interface_adapter.main;

import interface_adapter.ViewManagerModel;
import use_case.main.MainOutputBoundary;
import use_case.main.MainOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class MainPresenter implements MainOutputBoundary {

    private final MainViewModel mainViewModel;
    private final ViewManagerModel viewManagerModel;

    public MainPresenter(ViewManagerModel viewManagerModel, MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(MainOutputData response) {
        // On success, switch to the login view.
        // final LoginState loginState = loginViewModel.getState();
        // loginState.setUsername(response.getUsername());
        // this.loginViewModel.setState(loginState);
        // loginViewModel.firePropertyChanged();

        // viewManagerModel.setState(loginViewModel.getViewName());
        // viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // final SignupState signupState = signupViewModel.getState();
        // signupState.setUsernameError(error);
        // signupViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        // viewManagerModel.setState(loginViewModel.getViewName());
        // viewManagerModel.firePropertyChanged();
    }
}