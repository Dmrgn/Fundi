package use_case.dashboard;

/**
 * Output boundary for the Dashboard use case.
 */
public interface DashboardOutputBoundary {
    /**
     * Prepares the success view for the Dashboard use case.
     * 
     * @param outputData the output data
     */
    void prepareSuccessView(DashboardOutputData outputData);

    /**
     * Prepares the failure view for the Dashboard use case.
     * 
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
