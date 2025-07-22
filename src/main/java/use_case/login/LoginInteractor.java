package use_case.login;

import entity.User;
import use_case.UserDataAccessInterface;

public class LoginInteractor implements LoginInputBoundary {

    final UserDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;

    public LoginInteractor(UserDataAccessInterface userDataAccessInterface,
            LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        } else {
            String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for " + username + ".");
            } else {
                User user = userDataAccessObject.get(loginInputData.getUsername());
                LoginOutputData loginOutputData = new LoginOutputData(user.getName());
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}
