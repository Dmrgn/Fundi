package interface_adapter.change_password;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChangePwdViewModel {
    private ChangePwdState state = new ChangePwdState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ChangePwdState getState() {
        return state;
    }

    public void setState(ChangePwdState state) {
        this.state = state;
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
