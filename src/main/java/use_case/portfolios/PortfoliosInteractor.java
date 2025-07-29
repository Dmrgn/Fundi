package use_case.portfolios;

import java.util.Map;

/**
 * The Portfolios Interactor.
 */
public class PortfoliosInteractor implements PortfoliosInputBoundary {
    private final PortfoliosOutputBoundary portfoliosPresenter;
    private final PortfoliosDataAccessInterface portfoliosDataAccessInterface;

    public PortfoliosInteractor(PortfoliosOutputBoundary portfoliosPresenter, PortfoliosDataAccessInterface portfoliosDataAccessInterface) {
        this.portfoliosPresenter = portfoliosPresenter;
        this.portfoliosDataAccessInterface = portfoliosDataAccessInterface;
    }

    @Override
    public void execute(PortfoliosInputData portfoliosInputData) {
        Map<String, String> portfolios = portfoliosDataAccessInterface.getPortfolios(portfoliosInputData.getUsername());
        portfoliosPresenter.prepareView(new PortfoliosOutputData(portfoliosInputData.getUsername(), portfolios));
    }

    @Override
    public void routeToCreate(String username) {
        portfoliosPresenter.routeToCreate(username);
    }
}