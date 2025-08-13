package usecase.login;

import entity.User;

/**
 * Interactor for the Login Use Case.
 */
public class LoginInteractor implements LoginInputBoundary {

    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
            LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    /**
     * Execute the Login Use Case.
     * 
     * @param loginInputData the input data.
     */
    @Override
    public void execute(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        } else {
            String storedHash = userDataAccessObject.get(username).getPassword();
            if (!entity.PasswordHasher.matches(password, storedHash)) {
                loginPresenter.prepareFailView("Incorrect password for " + username + ".");
            } else {
                final User user = userDataAccessObject.get(loginInputData.getUsername());
                final LoginOutputData loginOutputData = new LoginOutputData(user.getName(), false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }

    /**
     * Switch to the Signup View.
     */
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }

    /**
     * Execute login with an already-hashed password (for remember me).
     */
    @Override
    public void executeWithHashedPassword(String username, String hashedPassword) {
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        } else if (!userDataAccessObject.authenticateWithHash(username, hashedPassword)) {
            loginPresenter.prepareFailView("Authentication failed for " + username + ".");
        } else {
            final User user = userDataAccessObject.get(username);
            final LoginOutputData loginOutputData = new LoginOutputData(user.getName(), false);
            loginPresenter.prepareSuccessView(loginOutputData);
        }
    }
}
