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
     * @param portfolioName the selected portfolio, null if going to make a new one
     */
    public void execute(String username, String portfolioName) {
        final PortfoliosInputData portfoliosInputData = new PortfoliosInputData(username, portfolioName);
        portfoliosUseCaseInteractor.execute(portfoliosInputData);
    }
}