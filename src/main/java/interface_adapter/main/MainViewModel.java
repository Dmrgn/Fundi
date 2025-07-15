package interface_adapter.main;

import interface_adapter.ViewModel;
import interface_adapter.main.MainState;

/**
 * The ViewModel for the Signup View.
 */
public class MainViewModel extends ViewModel<MainState> {

    public static final String TITLE_LABEL = "Main Title Label";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String TO_LOGIN_BUTTON_LABEL = "Check";

    public MainViewModel() {
        super("main");
        setState(new MainState());
    }

}