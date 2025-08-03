package interface_adapter.portfolioHub;

import use_case.portfolioHub.*;


/**
 * Controller for the Portfolios Use Case.
 */
public class PortfolioHubController {

    private final PortfolioHubInputBoundary portfoliosUseCaseInteractor;

    public PortfolioHubController(PortfolioHubInputBoundary portfoliosUseCaseInteractor) {
        this.portfoliosUseCaseInteractor = portfoliosUseCaseInteractor;
    }

    /**
     * Executes the Main Use Case.
     *
     * @param username the current user
     */
    public void execute(String username) {
        final PortfolioHubInputData portfoliosInputData = new PortfolioHubInputData(username);
        portfoliosUseCaseInteractor.execute(portfoliosInputData);
    }

    public void routeToCreate(String username) {
        portfoliosUseCaseInteractor.routeToCreate(username);
    }
}