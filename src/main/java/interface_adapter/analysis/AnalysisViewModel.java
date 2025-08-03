package interface_adapter.analysis;

import interface_adapter.ViewModel;

/**
 * The view model for the Analysis View
 */
public class AnalysisViewModel extends ViewModel<AnalysisState> {

    public AnalysisViewModel() {
        super("analysis");
        setState(new AnalysisState());
    }
}
