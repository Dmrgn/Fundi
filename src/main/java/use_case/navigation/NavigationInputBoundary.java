package use_case.navigation;

public interface NavigationInputBoundary {
    void goBack();

    void navigateTo(NavigationInputData inputData);
}
