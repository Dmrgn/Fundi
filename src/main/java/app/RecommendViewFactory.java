package app;

import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendViewModel;
import view.RecommendView;

public class RecommendViewFactory {
    private RecommendViewFactory() {

    }

    public static RecommendView create(
            RecommendViewModel recommendViewModel,
            RecommendController recommendController
    ) {
        return new RecommendView(recommendViewModel, recommendController);
    }
}
