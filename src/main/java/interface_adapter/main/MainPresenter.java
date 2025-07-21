package interface_adapter.main;

import interface_adapter.ViewManagerModel;
import use_case.main.MainOutputBoundary;
import use_case.main.MainOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class MainPresenter implements MainOutputBoundary {

    private final MainViewModel mainViewModel;
    private final ViewManagerModel viewManagerModel;

    public MainPresenter(ViewManagerModel viewManagerModel, MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareView() {
         final MainState mainState = mainViewModel.getState();
         this.mainViewModel.setState(mainState);
         mainViewModel.firePropertyChanged();

         viewManagerModel.setState(mainViewModel.getViewName());
         viewManagerModel.firePropertyChanged();
    }
}