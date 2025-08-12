package interfaceadapter.analysis;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.navigation.NavigationController;
import usecase.analysis.AnalysisOutputBoundary;
import usecase.analysis.AnalysisOutputData;

/**
 * The presenter for the analysis use .
 */
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

    /**
     * Prepare the analysis view.
     * @param analysisOutputData The analysis of the portfolio
     */
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

    /**
     * Switch to the portfolio view.
     */
    @Override
    public void routeToPortfolio() {
        viewManagerModel.setState("portfolio");
        viewManagerModel.firePropertyChanged();
    }
}
