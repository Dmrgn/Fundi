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

    /**
     * Switch to the Buy View.
     * @param portfolioId The portfolio id to update the state of the Buy View Model
     */
    void routeToBuy(String portfolioId);

    /**
     * Switch to the Sell View.
     * @param portfolioId The portfolio id to update the state of the Sell View Model
     */
    void routeToSell(String portfolioId);
}