package interfaceadapter.portfolio;

import interfaceadapter.portfolio_hub.PortfolioHubController;
import usecase.delete_portfolio.DeletePortfolioOutputBoundary;

public class DeletePortfolioPresenter implements DeletePortfolioOutputBoundary {
    private final PortfolioHubController portfolioHubController;

    public DeletePortfolioPresenter(PortfolioHubController portfolioHubController) {
        this.portfolioHubController = portfolioHubController;
    }

    @Override
    public void presentSuccess(String username) {
        System.out.println("DeletePortfolioPresenter: presentSuccess called with username: " + username);

        // Reload hub data and navigate - PortfolioHubController will handle the view
        // change
        portfolioHubController.execute(username);
        System.out.println(
                "DeletePortfolioPresenter: portfolioHubController.execute() completed - should have navigated to hub");
    }

    @Override
    public void presentFailure(String error) {
        System.err.println(error);
    }
}
