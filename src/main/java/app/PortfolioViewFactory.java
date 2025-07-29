package app;

import interface_adapter.analysis.AnalysisController;
import interface_adapter.history.HistoryController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.recommend.RecommendController;
import view.PortfolioView;
import view.PortfolioViewTemp;

public class PortfolioViewFactory {
    private PortfolioViewFactory() {

    }

    public static PortfolioViewTemp create(
            PortfolioViewModel portfolioViewModel,
            PortfolioController portfolioController,
            HistoryController historyController,
            AnalysisController analysisController,
            RecommendController recommendController
    ) {
        return new PortfolioViewTemp(
                portfolioViewModel,
                portfolioController,
                historyController,
                analysisController,
                recommendController
        );
    }
}
