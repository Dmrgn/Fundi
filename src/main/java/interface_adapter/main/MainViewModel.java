package interface_adapter.main;

import interface_adapter.ViewModel;

/**
 * The View Model for the Main View.
 */
public class MainViewModel extends ViewModel<MainState> {

    public MainViewModel() {
        super("main");
        setState(new MainState());
    }

}