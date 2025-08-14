package interfaceadapter.main;

import interfaceadapter.ViewModel;

/**
 * The View Model for the Main View.
 */
public class MainViewModel extends ViewModel<MainState> {

    public MainViewModel() {
        super("main");
        setState(new MainState());
    }

}
