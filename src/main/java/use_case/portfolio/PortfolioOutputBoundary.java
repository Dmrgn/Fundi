package use_case.portfolio;

/**
 * The output boundary for the Portfolio Use Case.
 */
public interface PortfolioOutputBoundary {
    /**
     * Prepares the view for the Portfolio Use Case.
     * @param outputData the output data
     */
    void prepareView(PortfolioOutputData outputData);

    void routeToBuy(String portfolioId);

    void routeToSell(String portfolioId);
}