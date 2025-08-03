package use_case.portfolio_hub;


/**
 * Input boundary for Portfolio Hub Use Case
 */
public interface PortfolioHubInputBoundary {

    /**
     * Executes the Portfolio Hub Use Case.
     * @param portfoliosInputData the input data.
     */
    void execute(PortfolioHubInputData portfoliosInputData);

    void routeToCreate(String username);
}
