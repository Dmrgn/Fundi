package interface_adapter.recommend;

import interface_adapter.ViewModel;

public class RecommendViewModel extends ViewModel<RecommendState> {

    public RecommendViewModel() {
        super("recommend");
        setState(new RecommendState());
    }
}
