package app;

import interface_adapter.analysis.AnalysisController;
import interface_adapter.analysis.AnalysisViewModel;
import view.AnalysisView;

public class AnalysisViewFactory {
    private AnalysisViewFactory() {

    }

    public static AnalysisView create(
            AnalysisViewModel analysisViewModel,
            AnalysisController analysisController
    ) {
        return new AnalysisView(analysisViewModel, analysisController);
    }
}

