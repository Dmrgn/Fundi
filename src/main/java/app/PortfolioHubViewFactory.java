package app;

import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolioHub.PortfolioHubController;
import interface_adapter.portfolioHub.PortfolioHubViewModel;
import interface_adapter.navigation.NavigationController;
import view.PortfolioHubView;

public class PortfolioHubViewFactory {
    private PortfolioHubViewFactory() {

    }

    public static PortfolioHubView create(PortfolioHubViewModel portfoliosViewModel, PortfolioHubController portfolioHubController,
                                          PortfolioController portfolioController, NavigationController navigationController) {
        return new PortfolioHubView(
                portfoliosViewModel,
                portfolioHubController,
                portfolioController,
                navigationController
        );
    }
}
