package use_case.portfolioHub;


/**
 * Input boundary for portfolios functionality
 */
public interface PortfolioHubInputBoundary {

    /**
     * Executes the portfolio usecase.
     * @param portfoliosInputData the input data.
     */
    void execute(PortfolioHubInputData portfoliosInputData);

    void routeToCreate(String username);
}
