package use_case.portfolioHub;

/**
 * The output boundary for the Portfolios Use Case.
 */
public interface PortfolioHubOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareView(PortfolioHubOutputData outputData);

    void routeToCreate(String username);

}