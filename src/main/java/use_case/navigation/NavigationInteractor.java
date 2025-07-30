package use_case.navigation;

import entity.NavigationState;

public class NavigationInteractor implements NavigationInputBoundary {
    private final NavigationState navigationState;
    private final NavigationOutputBoundary presenter;

    public NavigationInteractor(NavigationState navigationState,
            NavigationOutputBoundary presenter) {
        this.navigationState = navigationState;
        this.presenter = presenter;
    }

    @Override
    public void goBack() {
        String previousView = navigationState.popView();
        if (previousView != null) {
            NavigationOutputData outputData = new NavigationOutputData(
                    previousView, navigationState.canGoBack());
            presenter.presentBackNavigation(outputData);
        }
    }

    @Override
    public void navigateTo(NavigationInputData inputData) {
        // Only push to stack if not going to excluded views
        if (!isExcludedView(inputData.getTargetView())) {
            navigationState.pushView(inputData.getCurrentView());
        }
    }

    private boolean isExcludedView(String viewName) {
        return "login".equals(viewName) || "signup".equals(viewName) || "main".equals(viewName);
    }
}
