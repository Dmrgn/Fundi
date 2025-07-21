package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//import interface_adapter.change_password.ChangePasswordController;
//import interface_adapter.change_password.LoggedInState;
//import interface_adapter.change_password.LoggedInViewModel;
//import interface_adapter.logout.LogoutController;
import interface_adapter.login.LoginState;
import interface_adapter.main.MainController;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class MainView extends JPanel {

    private final String viewName = "main";
    private final MainViewModel mainViewModel;
    private MainController mainController;

    public MainView(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === 1. Top panel with plain text intro ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome to Fundi!");
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel usernameLabel = new JLabel();
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState state = mainViewModel.getState();
            usernameLabel.setText("Logged in as: " + state.getUsername());
        });
        usernameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(welcomeLabel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(usernameLabel);
        topPanel.add(Box.createVerticalStrut(10));

        // === 2. Image ===
        ImageIcon icon = new ImageIcon("resources/cover_photo.jpg");
        Image img = icon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(imgLabel);
        topPanel.add(Box.createVerticalStrut(15));

        // === 3. Buttons ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout(10, 10));
        JLabel promptLabel = new JLabel("What would you like to look at?");
        promptLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setMaximumSize(new Dimension(400, 100));

        String[] useCases = new String[] {"Portfolios", "Search", "News", "Watchlist", "Leaderboard", "Settings"};
        for (String useCase : useCases) {
            JButton useCaseButton = new JButton(useCase);

            useCaseButton.addActionListener(evt -> {
                final MainState currentState = mainViewModel.getState();
                currentState.setUseCase(useCase);  // set the correct use case at click time
                mainViewModel.setState(currentState);
                mainController.execute(
                        currentState.getUsername(),
                        currentState.getUseCase()
                );
            });
            buttonPanel.add(useCaseButton);
        }

        topPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(promptLabel, BorderLayout.NORTH);
        topPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add to main layout
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}