package use_case.dashboard;

/**
 * Input boundary for the Dashboard use case.
 */
public interface DashboardInputBoundary {
    /**
     * Execute the dashboard use case to get portfolio value history.
     * 
     * @param dashboardInputData the input data containing username
     */
    void execute(DashboardInputData dashboardInputData);
}
