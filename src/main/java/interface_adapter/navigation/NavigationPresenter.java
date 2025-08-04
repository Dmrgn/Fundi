package interface_adapter.navigation;

import interface_adapter.ViewManagerModel;
import use_case.navigation.NavigationOutputBoundary;
import use_case.navigation.NavigationOutputData;

public class NavigationPresenter implements NavigationOutputBoundary {
    private final ViewManagerModel viewManagerModel;

    public NavigationPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentBackNavigation(NavigationOutputData outputData) {
        viewManagerModel.setState(outputData.getTargetView());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void presentCompanyNavigation(NavigationOutputData outputData, String companySymbol) {
        // For now, just switch to the target view - the controller will handle the
        // details
        viewManagerModel.setState(outputData.getTargetView());
        viewManagerModel.firePropertyChanged();
    }
}
