package interface_adapter.portfolios;

import use_case.portfolios.*;


/**
 * Controller for the Portfolios Use Case.
 */
public class PortfoliosController {

    private final PortfoliosInputBoundary portfoliosUseCaseInteractor;

    public PortfoliosController(PortfoliosInputBoundary portfoliosUseCaseInteractor) {
        this.portfoliosUseCaseInteractor = portfoliosUseCaseInteractor;
    }

    /**
     * Executes the Main Use Case.
     *
     * @param username the current user
     */
    public void execute(String username) {
        final PortfoliosInputData portfoliosInputData = new PortfoliosInputData(username);
        portfoliosUseCaseInteractor.execute(portfoliosInputData);
    }

    public void routeToCreate(String username) {
        portfoliosUseCaseInteractor.routeToCreate(username);
    }
}