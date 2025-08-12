package usecase.navigation;

public interface NavigationOutputBoundary {
    void presentBackNavigation(NavigationOutputData outputData);

    void presentCompanyNavigation(NavigationOutputData outputData, String companySymbol);
}
