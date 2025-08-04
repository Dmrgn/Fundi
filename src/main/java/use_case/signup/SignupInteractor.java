package use_case.signup;

import entity.User;

/**
 * Interactor for the Signup Use Case.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
            SignupOutputBoundary signupOutputBoundary) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
    }

    /**
     * Interactor for the Signup Use Case.
     * @param signupInputData the input data
     */
    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        }

        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }

        else if (signupInputData.getUsername().trim().isEmpty()) {
            userPresenter.prepareFailView("Enter a valid username.");
        }

        else if (signupInputData.getPassword().trim().length() < 3) {
            userPresenter.prepareFailView("Enter a valid password at least 3 characters long.");
        }

        else {
            final User user = new User(signupInputData.getUsername(), signupInputData.getPassword());
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(user.getName(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    /**
     * Switch to the Login View.
     */
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
