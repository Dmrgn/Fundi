package app;

import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolios.PortfoliosController;
import interface_adapter.portfolios.PortfoliosViewModel;
import view.PortfoliosView;

public class PortfoliosViewFactory {
    private PortfoliosViewFactory() {

    }

    public static PortfoliosView create(PortfoliosViewModel portfoliosViewModel, PortfoliosController portfoliosController,
                                        PortfolioController portfolioController) {
        return new PortfoliosView(
                portfoliosViewModel,
                portfoliosController,
                portfolioController
        );
    }
}
