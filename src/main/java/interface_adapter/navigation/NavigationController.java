package interface_adapter.navigation;

import use_case.navigation.NavigationInputBoundary;
import use_case.navigation.NavigationInputData;

public class NavigationController {
    private final NavigationInputBoundary navigationInteractor;

    public NavigationController(NavigationInputBoundary navigationInteractor) {
        this.navigationInteractor = navigationInteractor;
    }

    public void goBack() {
        navigationInteractor.goBackFromCompany();
    }

    public void navigateTo(String currentView, String targetView) {
        NavigationInputData inputData = new NavigationInputData(currentView, targetView);
        navigationInteractor.navigateTo(inputData);
    }

    public void navigateToCompany(String currentView, String targetView, String companySymbol) {
        NavigationInputData inputData = new NavigationInputData(currentView, targetView);
        navigationInteractor.navigateToCompany(inputData, companySymbol);
    }
}
