package interface_adapter.analysis;

import interface_adapter.ViewModel;

public class AnalysisViewModel extends ViewModel<AnalysisState> {

    public AnalysisViewModel() {
        super("analysis");
        setState(new AnalysisState());
    }
}
