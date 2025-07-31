package use_case.navigation;

public class NavigationInputData {
    private final String currentView;
    private final String targetView;

    public NavigationInputData(String currentView, String targetView) {
        this.currentView = currentView;
        this.targetView = targetView;
    }

    public String getCurrentView() {
        return currentView;
    }

    public String getTargetView() {
        return targetView;
    }
}
