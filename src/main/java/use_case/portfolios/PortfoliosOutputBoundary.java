package use_case.portfolios;

/**
 * The output boundary for the Portfolios Use Case.
 */
public interface PortfoliosOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareView(PortfoliosOutputData outputData);

    void routeToCreate(String username);

}