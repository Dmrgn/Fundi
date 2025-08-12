package interfaceadapter.portfolio;

import usecase.portfolio.PortfolioInputBoundary;
import usecase.portfolio.PortfolioInputData;

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

    /**
     * Switch to the Buy View.
     * @param portfolioId Set the state information for the Buy View Model
     */
    public void routeToBuy(String portfolioId) {
        portfolioUseCaseInteractor.routeToBuy(portfolioId);
    }

    /**
     * Switch to the Sell View.
     * @param portfolioId Set the state information for the Sell View Model
     */
    public void routeToSell(String portfolioId) {
        portfolioUseCaseInteractor.routeToSell(portfolioId);
    }
}