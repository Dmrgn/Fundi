package view;

import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolio_hub.PortfolioHubController;
import view.components.ButtonFactory;
import view.components.FieldFactory;
import view.components.PanelFactory;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The Main View
 */
public class MainView extends BaseView {
    private final MainViewModel mainViewModel;
    private final PortfolioHubController portfolioHubController;
    private final NewsController newsController;
    private final NavigationController navigationController;
    private final SearchController searchController;
    private final SearchViewModel searchViewModel;
    private final JPanel searchResultsPanel = new JPanel();
    private final JButton notificationButton = createNotificationButton();
    private int notificationCount = 0;


    public MainView(MainViewModel mainViewModel, PortfolioHubController portfolioHubController, NewsController newsController, NavigationController navigationController, SearchController searchController, SearchViewModel searchViewModel) {
        super("main");
        this.mainViewModel = mainViewModel;
        this.portfolioHubController = portfolioHubController;
        this.newsController = newsController;
        this.navigationController = navigationController;
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;

        JPanel contentPanel = createGradientContentPanel();
        this.add(contentPanel, BorderLayout.CENTER);

        JPanel topPanel = createTopPanel();
        contentPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Add bottom panel with notification button
        JPanel bottomPanel = createBottomPanel();
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        // Welcome
        JPanel welcomePanel = PanelFactory.createTitlePanel("Welcome to Fundi!");
        JButton settingsButton = new JButton();
        try {
            ImageIcon gearIcon = new ImageIcon("resources/gear.png");
            Image gearImg = gearIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            settingsButton.setIcon(new ImageIcon(gearImg));
        } catch (Exception e) {
            settingsButton.setText("Settings");
        }
        settingsButton.setToolTipText("Settings");
        settingsButton.setContentAreaFilled(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setPreferredSize(new Dimension(40, 40));

        welcomePanel.add(settingsButton);

        // Search and Username
        JButton searchButton = ButtonFactory.createStyledButton("Search");
        JTextField searchField = FieldFactory.createTextField();
        JPanel searchPanel = PanelFactory.createSingleFieldForm(searchField, searchButton);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        this.mainViewModel.addPropertyChangeListener(evt -> {
                MainState mainState = mainViewModel.getState();
                usernameLabel.setText("Logged in as: " + mainState.getUsername());
        });
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerStack = new JPanel();
        centerStack.setLayout(new BoxLayout(centerStack, BoxLayout.Y_AXIS));
        centerStack.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerStack.setOpaque(false);
        centerStack.add(searchPanel);
        centerStack.add(Box.createVerticalStrut(5));
        centerStack.add(usernameLabel);

        JPanel centerRow = new JPanel();
        centerRow.setLayout(new BoxLayout(centerRow, BoxLayout.X_AXIS));
        centerRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerRow.setOpaque(false);
        centerRow.add(Box.createHorizontalGlue());
        centerRow.add(centerStack);
        centerRow.add(Box.createHorizontalGlue());

        topPanel.add(welcomePanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(centerRow);

        Runnable doSearch = () -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                this.searchController.execute(query); // <-- Call the use case
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search query.");
            }
        };
        searchButton.addActionListener(e -> doSearch.run());
        searchField.addActionListener(e -> doSearch.run());
        searchButton.addActionListener(evt -> doSearch.run());
        searchField.addActionListener(evt -> doSearch.run());

        return topPanel;
    }

    private JPanel createCenterPanel() {
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

        String[] useCases = { "Portfolios", "News", "Watchlist", "Leaderboard" };
        for (String useCase : useCases) {
            JButton useCaseButton = new JButton(useCase);
            useCaseButton.setFont(new Font("Sans Serif", Font.BOLD, 16));
            useCaseButton.setBackground(new Color(30, 60, 120));
            useCaseButton.setForeground(Color.WHITE);
            useCaseButton.setFocusPainted(false);
            useCaseButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            useCaseButton.addActionListener(evt -> {
                MainState mainState = mainViewModel.getState();
                mainState.setUseCase(useCase);
                mainViewModel.setState(mainState);
                this.navigationController.navigateTo(mainViewModel.getViewName(), useCase.toLowerCase());
                switch (useCase) {
                    case "Portfolios" -> this.portfolioHubController.execute(mainState.getUsername());
                    case "News" -> this.newsController.execute(mainState.getUsername());

                }
            });

            buttonPanel.add(useCaseButton);
        }

        centerPanel.add(promptLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel);

        return centerPanel;
    }

    // Add this method to create the notification button
    private JButton createNotificationButton() {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());

        // Create the main button
        JLabel iconLabel = new JLabel("🔔"); // Bell emoji as icon
        iconLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        button.add(iconLabel, BorderLayout.CENTER);

        // Create notification badge
        JLabel badge = new JLabel("0");
        badge.setFont(new Font("Sans Serif", Font.BOLD, 12));
        badge.setForeground(Color.WHITE);
        badge.setBackground(new Color(255, 59, 48)); // Red color
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        badge.setVisible(false); // Hidden by default
        button.add(badge, BorderLayout.EAST);

        // Style the button
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setContentAreaFilled(true);
                button.setBackground(new Color(40, 70, 130, 50));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
            }
        });

        return button;
    }

    // Modify the existing createBottomPanel method or add it if it doesn't exist
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Create notification button panel
        JPanel notificationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        notificationPanel.setOpaque(false);
        notificationPanel.add(notificationButton);

        bottomPanel.add(notificationPanel, BorderLayout.EAST);
        return bottomPanel;
    }

    // Add methods to control notifications
    public void incrementNotification() {
        notificationCount++;
        JLabel badge = (JLabel) notificationButton.getComponent(1);
        badge.setText(String.valueOf(notificationCount));
        badge.setVisible(true);
    }

    public void clearNotifications() {
        notificationCount = 0;
        JLabel badge = (JLabel) notificationButton.getComponent(1);
        badge.setText("0");
        badge.setVisible(false);
    }
}
