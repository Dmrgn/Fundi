package app;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupViewModel;
import view.SignupView;

/**
 * Factory for the Signup View.
 */
public final class SignupViewFactory {
    private SignupViewFactory() {

    }

    /**
     * Create the Signup View.
     * @param signupViewModel The Signup View Model
     * @param signupController The Signup Controller
     * @return The Signup View
     */
    public static SignupView create(
            SignupViewModel signupViewModel,
            SignupController signupController
    ) {
        return new SignupView(signupViewModel, signupController);
    }
}
