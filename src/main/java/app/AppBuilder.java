package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainViewModel;
import view.MainView;
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

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
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

        viewManagerModel.setState(mainView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}