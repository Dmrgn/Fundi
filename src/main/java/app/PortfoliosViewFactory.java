package app;

import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolios.PortfoliosController;
import interface_adapter.portfolios.PortfoliosViewModel;
import interface_adapter.navigation.NavigationController;
import view.PortfoliosView;

public class PortfoliosViewFactory {
    private PortfoliosViewFactory() {

    }

    public static PortfoliosView create(PortfoliosViewModel portfoliosViewModel, PortfoliosController portfoliosController,
                                        PortfolioController portfolioController, NavigationController navigationController) {
        return new PortfoliosView(
                portfoliosViewModel,
                portfoliosController,
                portfolioController,
                navigationController
        );
    }
}
