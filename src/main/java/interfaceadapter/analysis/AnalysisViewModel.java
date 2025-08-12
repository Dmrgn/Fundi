package interfaceadapter.analysis;

import interfaceadapter.ViewModel;

/**
 * The view model for the Analysis View.
 */
public class AnalysisViewModel extends ViewModel<AnalysisState> {

    public AnalysisViewModel() {
        super("analysis");
        setState(new AnalysisState());
    }
}
