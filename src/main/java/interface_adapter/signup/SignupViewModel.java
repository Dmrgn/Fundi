package interface_adapter.signup;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;

public class SignupViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Sign Up View";
    public static final String USERNAME_LABEL = "Username";
    public static final String PASSWORD_LABEL = "Password";
    public static final String REPEAT_PASSWORD_LABEL = "Confirm Password";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String LOGIN_BUTTON_LABEL = "Log in Instead";

    private SignupState state = new SignupState();

    public SignupViewModel() {
        super("signup");
    }

    public void setState(SignupState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SignupState getState() {
        return state;
    }
}
