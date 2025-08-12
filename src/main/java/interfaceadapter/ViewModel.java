package interfaceadapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewModel for our CA implementation.
 * This class delegates work to a PropertyChangeSupport object for
 * managing the property change events.
 *
 * @param <T> The type of state object contained in the model.
 */
public class ViewModel<T> {

    private final String viewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private T state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    /**
     * Getter.
     * @return View Name
     */
    public String getViewName() {
        return this.viewName;
    }

    /**
     * Getter.
     * @return The State
     */
    public T getState() {
        return this.state;
    }

    /**
     * Setter.
     * @param state Value
     */
    public void setState(T state) {
        this.state = state;
    }

    /**
     * Fires a property changed event for the state of this ViewModel.
     */
    public void firePropertyChanged() {
        this.support.firePropertyChange("state", null, this.state);
    }

    /**
     * Fires a property changed event for the state of this ViewModel, which
     * allows the user to specify a different propertyName. This can be useful
     * when a class is listening for multiple kinds of property changes.
     * @param propertyName the label for the property that was changed
     */
    public void firePropertyChanged(String propertyName) {
        this.support.firePropertyChange(propertyName, null, this.state);
    }

    /**
     * Adds a PropertyChangeListener to this ViewModel.
     * 
     * @param listener The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}