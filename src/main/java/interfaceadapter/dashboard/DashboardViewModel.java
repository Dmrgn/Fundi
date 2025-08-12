package interfaceadapter.dashboard;

import interfaceadapter.ViewModel;

/**
 * The View Model for the Dashboard View.
 */
public class DashboardViewModel extends ViewModel<DashboardState> {

    public DashboardViewModel() {
        super("dashboard");
        setState(new DashboardState());
    }
}
