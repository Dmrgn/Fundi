package app;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.recommend.RecommendController;
import interfaceadapter.recommend.RecommendViewModel;
import view.RecommendView;

/**
 * Factory for the Recommend View.
 */
public final class RecommendViewFactory {
    private RecommendViewFactory() {

    }

    /**
     * Create the Recommend View.
     * @param recommendViewModel The Recommend View Model
     * @param recommendController The Recommend Controller
     * @param viewManagerModel The View Manager Model
     * @return The Recommend View
     */
    public static RecommendView create(
            RecommendViewModel recommendViewModel,
            RecommendController recommendController,
            ViewManagerModel viewManagerModel) {
        return new RecommendView(recommendViewModel, recommendController, viewManagerModel);
    }
}
