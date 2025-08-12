package interfaceadapter.login;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interfaceadapter.ViewModel;

/**
 * The view model for the Login View.
 */
public class LoginViewModel extends ViewModel<LoginState> {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private LoginState state = new LoginState();

    public LoginViewModel() {
        super("log in");
    }

    @Override
    public LoginState getState() {
        return state;
    }

    @Override
    public void setState(LoginState state) {
        this.state = state;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
