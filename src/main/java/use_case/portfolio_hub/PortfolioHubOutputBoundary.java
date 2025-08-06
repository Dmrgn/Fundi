package use_case.portfolio_hub;

/**
 * The output boundary for the Portfolio Hub Use Case.
 */
public interface PortfolioHubOutputBoundary {
    /**
     * Prepares the success view for the Portfolio Hub Use Case.
     * @param outputData the output data
     */
    void prepareView(PortfolioHubOutputData outputData);

    /**
     * Switch to the Create View.
     * @param username The username to update the state of the Create View Model
     */
    void routeToCreate(String username);

}