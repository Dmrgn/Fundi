package view;

import interface_adapter.ViewManagerModel;

/**
 * Simple utility class for handling back navigation without complex stack
 * management.
 * Each method provides a direct, predictable navigation path.
 */
public class BackNavigationHelper {
    private final ViewManagerModel viewManagerModel;

    public BackNavigationHelper(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Navigate back to the portfolios tab in the main tabbed view.
     * Used by CreateView and other portfolio-related views.
     */
    public void goBackToPortfolios() {
        viewManagerModel.setState("tabbedmain");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Navigate back to the individual portfolio view.
     * Used by BuyView, SellView, HistoryView, AnalysisView, and RecommendView.
     */
    public void goBackToPortfolio() {
        viewManagerModel.setState("portfolio");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Navigate back to the main tabbed view (dashboard).
     * Used by views that should return to the main interface.
     */
    public void goBackToMain() {
        viewManagerModel.setState("tabbedmain");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Generic navigation method for custom back destinations.
     */
    public void goBackTo(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.firePropertyChanged();
    }
}
