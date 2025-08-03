package app;

import interface_adapter.analysis.AnalysisController;
import interface_adapter.history.HistoryController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.recommend.RecommendController;
import interface_adapter.ViewManagerModel;
import view.PortfolioView;

public class PortfolioViewFactory {
    private PortfolioViewFactory() {

    }

    public static PortfolioView create(
            PortfolioViewModel portfolioViewModel,
            PortfolioController portfolioController,
            HistoryController historyController,
            AnalysisController analysisController,
            RecommendController recommendController,
            ViewManagerModel viewManagerModel

    ) {
        return new PortfolioView(
                portfolioViewModel,
                portfolioController,
                historyController,
                analysisController,
                recommendController,
                viewManagerModel);
    }
}
