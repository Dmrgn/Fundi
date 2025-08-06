package app;

import interface_adapter.navigation.NavigationController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio_hub.PortfolioHubController;
import interface_adapter.portfolio_hub.PortfolioHubViewModel;
import view.PortfolioHubView;

/**
 * Factory for the Portfolio Hub View.
 */
public final class PortfolioHubViewFactory {
    private PortfolioHubViewFactory() {

    }

    /**
     * Create the Portfolio Hub View.
     * @param portfolioHubViewModel The Portfolio Hub View Model
     * @param portfolioHubController The Portfolio Hub Controller
     * @param portfolioController The Portfolio Controller
     * @param navigationController The Navigation Controller
     * @return The Portfolio Hub View
     */
    public static PortfolioHubView create(PortfolioHubViewModel portfolioHubViewModel, PortfolioHubController portfolioHubController,
                                          PortfolioController portfolioController, NavigationController navigationController) {
        return new PortfolioHubView(
                portfolioHubViewModel,
                portfolioHubController,
                portfolioController,
                navigationController
        );
    }
}
