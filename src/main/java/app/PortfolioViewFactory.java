package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.analysis.AnalysisController;
import interfaceadapter.history.HistoryController;
import interfaceadapter.portfolio.PortfolioController;
import interfaceadapter.portfolio.DeletePortfolioController;
import interfaceadapter.portfolio.PortfolioViewModel;
import interfaceadapter.recommend.RecommendController;
import view.PortfolioView;

/**
 * Factory for the Portfolio View.
 */
public final class PortfolioViewFactory {
    private PortfolioViewFactory() {

    }

    /**
     * Create the Portfolio View.
     * 
     * @param portfolioViewModel        The Portfolio View Model
     * @param portfolioController       The Portfolio Controller
     * @param historyController         The History Controller
     * @param analysisController        The Analysis Controller
     * @param recommendController       The Recommend Controller
     * @param viewManagerModel          The View Manager Model
     * @param deletePortfolioController The Delete Portfolio Controller
     * @return The Portfolio View
     */
    public static PortfolioView create(
            PortfolioViewModel portfolioViewModel,
            PortfolioController portfolioController,
            HistoryController historyController,
            AnalysisController analysisController,
            RecommendController recommendController,
            ViewManagerModel viewManagerModel,
            DeletePortfolioController deletePortfolioController

    ) {
        return new PortfolioView(
                portfolioViewModel,
                portfolioController,
                historyController,
                analysisController,
                recommendController,
                viewManagerModel,
                deletePortfolioController);
    }
}
