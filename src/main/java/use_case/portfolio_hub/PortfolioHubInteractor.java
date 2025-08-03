package use_case.portfolio_hub;

import java.util.Map;

/**
 * Interactor for the Portfolio Hub Use Case.
 */
public class PortfolioHubInteractor implements PortfolioHubInputBoundary {
    private final PortfolioHubOutputBoundary portfoliosPresenter;
    private final PortfolioHubDataAccessInterface portfoliosDataAccessInterface;

    public PortfolioHubInteractor(PortfolioHubOutputBoundary portfoliosPresenter, PortfolioHubDataAccessInterface portfoliosDataAccessInterface) {
        this.portfoliosPresenter = portfoliosPresenter;
        this.portfoliosDataAccessInterface = portfoliosDataAccessInterface;
    }

    /**
     * Execute the Portfolio Hub Use Case
     * @param portfoliosInputData the input data.
     */
    @Override
    public void execute(PortfolioHubInputData portfoliosInputData) {
        Map<String, String> portfolios = portfoliosDataAccessInterface.getPortfolios(portfoliosInputData.getUsername());
        portfoliosPresenter.prepareView(new PortfolioHubOutputData(portfoliosInputData.getUsername(), portfolios));
    }

    /**
     * Switch to the Create View
     * @param username The username to update the state of the Create View Model
     */
    @Override
    public void routeToCreate(String username) {
        portfoliosPresenter.routeToCreate(username);
    }
}