package interface_adapter.portfolios;

import use_case.portfolios.*;

import java.util.Map;

/**
 * The Portfolios Interactor.
 */
public class PortfoliosInteractor implements PortfoliosInputBoundary {
    private final PortfoliosOutputBoundary portfoliosPresenter;

    public PortfoliosInteractor(PortfoliosOutputBoundary portfoliosPresenter) {
        this.portfoliosPresenter = portfoliosPresenter;
    }

    @Override
    public void execute(PortfoliosInputData portfoliosInputData) {
        portfoliosPresenter.prepareView(new PortfoliosOutputData(portfoliosInputData.getUsername(),
                portfoliosInputData.getPortfolioName()));
    }
}