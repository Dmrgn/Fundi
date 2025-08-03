package interface_adapter.portfolio_hub;

import use_case.portfolio_hub.*;


/**
 * Controller for the Portfolio Hub Use Case.
 */
public class PortfolioHubController {

    private final PortfolioHubInputBoundary portfoliosUseCaseInteractor;

    public PortfolioHubController(PortfolioHubInputBoundary portfoliosUseCaseInteractor) {
        this.portfoliosUseCaseInteractor = portfoliosUseCaseInteractor;
    }

    /**
     * Executes the Portfolio Hub Use Case.
     * @param username the current user
     */
    public void execute(String username) {
        final PortfolioHubInputData portfoliosInputData = new PortfolioHubInputData(username);
        portfoliosUseCaseInteractor.execute(portfoliosInputData);
    }

    /**
     * Switch to the Create View
     * @param username The username to update the state of the Create View Model
     */
    public void routeToCreate(String username) {
        portfoliosUseCaseInteractor.routeToCreate(username);
    }
}