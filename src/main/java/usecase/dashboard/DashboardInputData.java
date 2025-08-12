package usecase.dashboard;

/**
 * Input data for the Dashboard use case.
 */
public class DashboardInputData {
    private final String username;

    public DashboardInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
