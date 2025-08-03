package app;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupViewModel;
import view.SignupView;

/**
 * Factory for the Signup View
 */
public class SignupViewFactory {
    private SignupViewFactory() {

    }

    public static SignupView create(
            SignupViewModel signupViewModel,
            SignupController signupController
    ) {
        return new SignupView(signupViewModel, signupController);
    }
}
