package usecase.portfolio_hub;


/**
 * Input boundary for Portfolio Hub Use Case.
 */
public interface PortfolioHubInputBoundary {

    /**
     * Executes the Portfolio Hub Use Case.
     * @param portfoliosInputData the input data.
     */
    void execute(PortfolioHubInputData portfoliosInputData);

    /**
     * Switch to the Create View.
     * @param username The username to update the Create View Model State
     */
    void routeToCreate(String username);
}
