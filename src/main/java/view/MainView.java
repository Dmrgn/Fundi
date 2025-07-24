package view;

import java.awt.*;

import javax.swing.*;

//import interface_adapter.change_password.ChangePasswordController;
//import interface_adapter.change_password.LoggedInState;
//import interface_adapter.change_password.LoggedInViewModel;
//import interface_adapter.logout.LogoutController;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolios.PortfoliosController;

/**
 * The View for when the user is logged into the program.
 */
public class MainView extends JPanel {

    private final String viewName = "main";
    private final MainViewModel mainViewModel;
    private PortfoliosController portfoliosController;

    public MainView(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Set dark blue gradient background for the whole panel
        setOpaque(false);
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(10, 30, 60);
                Color color2 = new Color(30, 60, 120);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setOpaque(false);


        // === 1. Top panel with welcome, username, and search bar ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);


        // Top row: Welcome label (centered) and gear icon (top right)
        JPanel welcomeRow = new JPanel();
        welcomeRow.setLayout(new BoxLayout(welcomeRow, BoxLayout.X_AXIS));
        welcomeRow.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Welcome to Fundi!");
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 48));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        JButton settingsButton = new JButton();
        try {
            ImageIcon gearIcon = new ImageIcon("resources/gear.png");
            Image gearImg = gearIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            settingsButton.setIcon(new ImageIcon(gearImg));
        } catch (Exception e) {
            settingsButton.setText("Settings");
        }
        settingsButton.setPreferredSize(new Dimension(40, 40));
        settingsButton.setMaximumSize(new Dimension(40, 40));
        settingsButton.setContentAreaFilled(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setToolTipText("Settings");
        // TODO: Add settings action here
        welcomeLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        settingsButton.setAlignmentY(Component.TOP_ALIGNMENT);
        welcomeRow.add(Box.createHorizontalGlue());
        welcomeRow.add(welcomeLabel);
        welcomeRow.add(Box.createHorizontalGlue());
        welcomeRow.add(settingsButton);
        topPanel.add(welcomeRow);
        topPanel.add(Box.createVerticalStrut(5));

        // Search bar and username (right-aligned, stacked)
        JPanel rightStackPanel = new JPanel();
        rightStackPanel.setLayout(new BoxLayout(rightStackPanel, BoxLayout.Y_AXIS));
        rightStackPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightStackPanel.setOpaque(false);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        searchPanel.setOpaque(false);
        JTextField searchField = new JTextField(16);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setMaximumSize(new Dimension(200, 30));
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(80, 30));
        searchButton.setMaximumSize(new Dimension(80, 30));
        searchPanel.add(Box.createHorizontalGlue());
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchButton);

        rightStackPanel.add(searchPanel);
        rightStackPanel.add(Box.createVerticalStrut(5));

        JLabel usernameLabel = new JLabel();
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState state = mainViewModel.getState();
            usernameLabel.setText("Logged in as: " + state.getUsername());
        });
        usernameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightStackPanel.add(usernameLabel);

        JPanel rightRow = new JPanel();
        rightRow.setLayout(new BoxLayout(rightRow, BoxLayout.X_AXIS));
        rightRow.setOpaque(false);
        rightRow.add(Box.createHorizontalGlue());
        rightRow.add(rightStackPanel);
        topPanel.add(rightRow);
        topPanel.add(Box.createVerticalStrut(10));

        // (Image removed)

        // Dummy search action
        Runnable doSearch = () -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Searching for: " + query);
            }
        };
        searchButton.addActionListener(e -> doSearch.run());
        searchField.addActionListener(e -> doSearch.run());
        

        // === 3. Buttons ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        JLabel promptLabel = new JLabel("What would you like to look at?");
        promptLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        promptLabel.setForeground(Color.WHITE);
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setMaximumSize(new Dimension(400, 100));
        buttonPanel.setOpaque(false);

<<<<<<< HEAD
        String[] useCases = new String[] { "Portfolios", "Search", "News", "Watchlist", "Leaderboard", "Settings" };
=======
        String[] useCases = new String[] {"Portfolios", "News", "Watchlist", "Leaderboard"};
>>>>>>> Varak
        for (String useCase : useCases) {
            JButton useCaseButton = new JButton(useCase);
            useCaseButton.setFont(new Font("Sans Serif", Font.BOLD, 16));
            useCaseButton.setBackground(new Color(30, 60, 120));
            useCaseButton.setForeground(Color.WHITE);
            useCaseButton.setFocusPainted(false);
            useCaseButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            useCaseButton.addActionListener(evt -> {
                final MainState currentState = mainViewModel.getState();
<<<<<<< HEAD
                currentState.setUseCase(useCase); // set the correct use case at click time
=======
                currentState.setUseCase(useCase);
>>>>>>> Varak
                mainViewModel.setState(currentState);
                if (useCase.equals("Portfolios")) {
                    portfoliosController.execute(currentState.getUsername());
                }
            });
            buttonPanel.add(useCaseButton);
        }

        centerPanel.add(promptLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add to main layout
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(PortfoliosController portfoliosController) {
        this.portfoliosController = portfoliosController;
    }
}
