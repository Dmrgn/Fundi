package interface_adapter.portfolio;

import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioInputData;

/**
 * The controller for the Portfolio Use Case.
 */
public class PortfolioController {

    private final PortfolioInputBoundary portfolioUseCaseInteractor;

    public PortfolioController(PortfolioInputBoundary portfolioUseCaseInteractor) {
        this.portfolioUseCaseInteractor = portfolioUseCaseInteractor;
    }

    /**
     * Executes the Portfolio Use Case.
     * @param username the username
     * @param portfolioId the id of the portfolio
     * @param portfolioName the name of the portfolio
     */
    public void execute(String username, String portfolioId, String portfolioName) {
        final PortfolioInputData portfolioInputData = new PortfolioInputData(username, portfolioId, portfolioName);

        portfolioUseCaseInteractor.execute(portfolioInputData);
    }

    public void routeToBuy(String portfolioId) {
        portfolioUseCaseInteractor.routeToBuy(portfolioId);
    }

    public void routeToSell(String portfolioId) {
        portfolioUseCaseInteractor.routeToSell(portfolioId);
    }

    public void routeToPortfolios() {
        portfolioUseCaseInteractor.routeToPortfolios();
    }
}