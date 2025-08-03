package use_case.portfolioHub;

import java.util.Map;

/**
 * The Portfolios Interactor.
 */
public class PortfolioHubInteractor implements PortfolioHubInputBoundary {
    private final PortfolioHubOutputBoundary portfoliosPresenter;
    private final PortfolioHubDataAccessInterface portfoliosDataAccessInterface;

    public PortfolioHubInteractor(PortfolioHubOutputBoundary portfoliosPresenter, PortfolioHubDataAccessInterface portfoliosDataAccessInterface) {
        this.portfoliosPresenter = portfoliosPresenter;
        this.portfoliosDataAccessInterface = portfoliosDataAccessInterface;
    }

    @Override
    public void execute(PortfolioHubInputData portfoliosInputData) {
        Map<String, String> portfolios = portfoliosDataAccessInterface.getPortfolios(portfoliosInputData.getUsername());
        portfoliosPresenter.prepareView(new PortfolioHubOutputData(portfoliosInputData.getUsername(), portfolios));
    }

    @Override
    public void routeToCreate(String username) {
        portfoliosPresenter.routeToCreate(username);
    }
}