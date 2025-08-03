package app;

import interface_adapter.analysis.AnalysisViewModel;
import interface_adapter.navigation.NavigationController;
import view.AnalysisView;

/**
 * Factory for the Analysis View
 */
public class AnalysisViewFactory {
    private AnalysisViewFactory() {

    }

    public static AnalysisView create(
            AnalysisViewModel analysisViewModel,
            NavigationController navigationController
    ) {
        return new AnalysisView(analysisViewModel, navigationController);
    }
}

