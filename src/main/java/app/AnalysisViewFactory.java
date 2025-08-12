package app;

import interfaceadapter.analysis.AnalysisViewModel;
import interfaceadapter.navigation.NavigationController;
import view.AnalysisView;

/**
 * Factory for the Analysis View.
 */
public final class AnalysisViewFactory {
    private AnalysisViewFactory() {

    }

    /**
     * Create the Analysis View.
     * @param analysisViewModel The Analysis View Model
     * @param navigationController The Navigation Controller
     * @return The Analysis View
     */
    public static AnalysisView create(
            AnalysisViewModel analysisViewModel,
            NavigationController navigationController
    ) {
        return new AnalysisView(analysisViewModel, navigationController);
    }
}

