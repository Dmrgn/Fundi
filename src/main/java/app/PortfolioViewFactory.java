package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.analysis.AnalysisController;
import interface_adapter.history.HistoryController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.recommend.RecommendController;
import view.PortfolioView;

/**
 * Factory for the Portfolio View.
 */
public final class PortfolioViewFactory {
    private PortfolioViewFactory() {

    }

    /**
     * Create the Portfolio View.
     * @param portfolioViewModel The Portfolio View Model
     * @param portfolioController The Portfolio Controller
     * @param historyController The History Controller
     * @param analysisController The Analysis Controller
     * @param recommendController The Recommend Controller
     * @param viewManagerModel The View Manager Model
     * @return The Portfolio View
     */
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
