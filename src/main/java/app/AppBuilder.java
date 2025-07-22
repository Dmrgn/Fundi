package app;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.FileUserDataAccessObject;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.UserDataAccessInterface;
import view.LoginView;
import view.MainView;
import view.SignupView;
import view.ViewManager;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private MainView mainView;
    private MainViewModel mainViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private SignupView signupView;
    private SignupViewModel signupViewModel;

    private UserDataAccessInterface userDataAccessObject;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        try {
            userDataAccessObject = new FileUserDataAccessObject("users.csv", new UserFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds the Signup View to the application.
     * 
     * @return this builder
     */
    public AppBuilder addMainView() {
        mainViewModel = new MainViewModel();
        mainView = new MainView(mainViewModel);
        cardPanel.add(mainView, mainView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, mainViewModel, userDataAccessObject);
        cardPanel.add(loginView, loginView.viewName);
        return this;
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel,
                userDataAccessObject);
        cardPanel.add(signupView, signupView.viewName);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to
     * be displayed.
     * 
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Fundi Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
