package interfaceadapter.change_password;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChangePwdViewModel {
    private ChangePwdState state = new ChangePwdState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Returns the current state used by the view.
     *
     * @return current state
     */
    public ChangePwdState getState() {
        return state;
    }

    /**
     * Updates the state and notifies the listeners.
     *
     * @param state new state
     */
    public void setState(ChangePwdState state) {
        this.state = state;
        support.firePropertyChange("state", null, state);
    }

    /**
     * Registers a listener for state updates.
     * @param listener listener to add
     */

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
