package use_case.signup;

import entity.User;
import entity.UserFactory;
import use_case.UserDataAccessInterface;

public class SignupInteractor implements SignupInputBoundary {
    final UserDataAccessInterface userDataAccessObject;
    final SignupOutputBoundary userPresenter;
    final UserFactory userFactory;

    public SignupInteractor(UserDataAccessInterface signupDataAccessInterface,
            SignupOutputBoundary signupOutputBoundary,
            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        } else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        } else if (signupInputData.getUsername().trim().length() == 0) {
            userPresenter.prepareFailView("Enter a valid username.");
        } else if (signupInputData.getPassword().trim().length() < 3) {
            userPresenter.prepareFailView("Enter a valid password at least 3 characters long.");
        } else {
            User user = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword());
            userDataAccessObject.save(user);

            SignupOutputData signupOutputData = new SignupOutputData(user.getName());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }
}
