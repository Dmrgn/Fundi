package interfaceadapter.portfolio;

import usecase.delete_portfolio.DeletePortfolioInputBoundary;
import usecase.delete_portfolio.DeletePortfolioInputData;

public class DeletePortfolioController {
    private final DeletePortfolioInputBoundary interactor;

    public DeletePortfolioController(DeletePortfolioInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String portfolioName) {
        interactor.execute(new DeletePortfolioInputData(username, portfolioName));
    }
}
