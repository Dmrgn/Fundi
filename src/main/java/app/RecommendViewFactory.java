package app;

import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendViewModel;
import interface_adapter.navigation.NavigationController;
import view.RecommendView;

public class RecommendViewFactory {
    private RecommendViewFactory() {

    }

    public static RecommendView create(
            RecommendViewModel recommendViewModel,
            RecommendController recommendController,
            NavigationController navigationController
    ) {
        return new RecommendView(recommendViewModel, recommendController, navigationController);
    }
}
