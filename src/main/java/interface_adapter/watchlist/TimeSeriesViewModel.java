package interface_adapter.watchlist;

import entity.TimeSeriesPoint;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.List;

public class TimeSeriesViewModel {
    public static final String PROP_UPDATE = "timeseries_update";

    private final SwingPropertyChangeSupport support = new SwingPropertyChangeSupport(this);

    private String symbol;
    private List<TimeSeriesPoint> points;
    private String error;
    private String range;

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }

    public void setData(String symbol, List<TimeSeriesPoint> pts, String err, String range) {
        this.symbol = symbol;
        this.points = pts;
        this.error = err;
        this.range = range;
        support.firePropertyChange(PROP_UPDATE, null, null);
    }

    public String getSymbol() {
        return symbol;
    }

    public List<TimeSeriesPoint> getPoints() {
        return points;
    }

    public String getError() {
        return error;
    }

    public String getRange() {
        return range;
    }
}
