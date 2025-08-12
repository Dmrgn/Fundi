package interfaceadapter.navigation;

import interfaceadapter.ViewManagerModel;
import usecase.navigation.NavigationOutputBoundary;
import usecase.navigation.NavigationOutputData;

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
