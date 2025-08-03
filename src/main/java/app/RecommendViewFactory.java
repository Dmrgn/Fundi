package app;

import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendViewModel;
import interface_adapter.ViewManagerModel;
import view.RecommendView;

/**
 * Factory for the Recommend View
 */
public class RecommendViewFactory {
    private RecommendViewFactory() {

    }

    public static RecommendView create(
            RecommendViewModel recommendViewModel,
            RecommendController recommendController,
            ViewManagerModel viewManagerModel) {
        return new RecommendView(recommendViewModel, recommendController, viewManagerModel);
    }
}
