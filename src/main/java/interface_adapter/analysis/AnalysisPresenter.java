package interface_adapter.analysis;

import interface_adapter.ViewManagerModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.sell.SellState;
import use_case.analysis.AnalysisOutputBoundary;
import use_case.analysis.AnalysisOutputData;

public class AnalysisPresenter implements AnalysisOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final AnalysisViewModel analysisViewModel;
    private final NavigationController navigationController;


    public AnalysisPresenter(ViewManagerModel viewManagerModel, AnalysisViewModel analysisViewModel, 
                             NavigationController navigationController) {
        this.viewManagerModel = viewManagerModel;
        this.analysisViewModel = analysisViewModel;
        this.navigationController = navigationController;
    }

    @Override
    public void prepareView(AnalysisOutputData analysisOutputData) {
        AnalysisState analysisState = analysisViewModel.getState();
        analysisState.setNumTickers(analysisOutputData.getNumTickers());
        analysisState.setMajorityTickers(analysisOutputData.getMajorityTickers());
        analysisState.setVolatility(analysisOutputData.getVolatility());
        analysisState.setMostVolTickers(analysisOutputData.getMostVolTickers());
        analysisState.setLeastVolTickers(analysisOutputData.getLeastVolTickers());
        analysisState.setPastReturn(analysisOutputData.getPastReturn());
        analysisState.setTopReturns(analysisOutputData.getTopReturns());
        analysisState.setWorstReturns(analysisOutputData.getWorstReturns());
        analysisViewModel.setState(analysisState);
        this.analysisViewModel.firePropertyChanged();
        navigationController.navigateTo(viewManagerModel.getState(), "analysis");
        this.viewManagerModel.setState(analysisViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

    }

    @Override
    public void routeToPortfolio() {
        viewManagerModel.setState("portfolio");
        viewManagerModel.firePropertyChanged();
    }
}
