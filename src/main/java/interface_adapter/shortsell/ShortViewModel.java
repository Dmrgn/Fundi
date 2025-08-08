package interface_adapter.shortsell;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShortViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final String viewName = "short";
    private ShortState state = new ShortState();

    public String getViewName() { return viewName; }
    public ShortState getState() { return state; }
    public void setState(ShortState state) { this.state = state; }
    public void firePropertyChanged() { support.firePropertyChange("state", null, state); }
    public void addPropertyChangeListener(PropertyChangeListener l) { support.addPropertyChangeListener(l); }
}
