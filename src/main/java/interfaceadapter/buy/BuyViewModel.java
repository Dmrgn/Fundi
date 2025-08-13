package interfaceadapter.buy;

import interfaceadapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The view model for the Buy View.
 */
public class BuyViewModel extends ViewModel<BuyState> {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private BuyState state = new BuyState();

    public BuyViewModel() {
        super("buy");
        setState(new BuyState());
    }

    public void setState(BuyState state) {
        this.state = state;
        support.firePropertyChange("state", null, this.state);
    }

    public BuyState getState() {
        return state;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }
}
