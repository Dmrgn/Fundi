package interface_adapter.recommend;

import interface_adapter.ViewModel;

/**
 * The View Model for the Recommend View
 */
public class RecommendViewModel extends ViewModel<RecommendState> {

    public RecommendViewModel() {
        super("recommend");
        setState(new RecommendState());
    }
}
