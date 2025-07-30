package use_case.navigation;

public class NavigationOutputData {
    private final String targetView;
    private final boolean canGoBack;

    public NavigationOutputData(String targetView, boolean canGoBack) {
        this.targetView = targetView;
        this.canGoBack = canGoBack;
    }

    public String getTargetView() {
        return targetView;
    }

    public boolean canGoBack() {
        return canGoBack;
    }
}
