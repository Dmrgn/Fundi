package view;

import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolio_hub.PortfolioHubController;
import view.ui.ButtonFactory;
import view.ui.UiConstants;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * The Main View
 */
public class MainView extends BaseView {
    private final MainViewModel mainViewModel;
    private final PortfolioHubController portfolioHubController;
    private final NewsController newsController;
    private final NavigationController navigationController;
    private final SearchController searchController;
    private JButton notificationButton; // Will be initialized in constructor
    private int notificationCount = 0;


    public MainView(MainViewModel mainViewModel, PortfolioHubController portfolioHubController, NewsController newsController, NavigationController navigationController, SearchController searchController, SearchViewModel searchViewModel) {
        super("main");
        this.mainViewModel = mainViewModel;
        this.portfolioHubController = portfolioHubController;
        this.newsController = newsController;
        this.navigationController = navigationController;
        this.searchController = searchController;
        // Note: searchViewModel parameter is kept for API compatibility but not stored

        // Use modern layout similar to Login/Signup views
        setLayout(new GridBagLayout());
        setBackground(UiConstants.PRIMARY_COLOUR);
        
        // Create main light gray panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray instead of white
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(10, 10, 10, 10);
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.anchor = GridBagConstraints.CENTER;
        mainGbc.weightx = 1.0;
        
        // Add components to main panel
        JPanel topSection = createTopSection();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.gridwidth = 1;
        mainPanel.add(topSection, mainGbc);
        
        mainGbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(30), mainGbc);
        
        JPanel centerSection = createCenterSection();
        mainGbc.gridy++;
        mainPanel.add(centerSection, mainGbc);
        
        mainGbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(20), mainGbc);
        
        // Add notification bell directly to bottom left
        JButton notificationBell = createModernNotificationButton();
        GridBagConstraints bellGbc = new GridBagConstraints();
        bellGbc.gridx = 0;
        bellGbc.gridy = mainGbc.gridy + 1;
        bellGbc.anchor = GridBagConstraints.SOUTHWEST;
        bellGbc.insets = new Insets(10, 10, 10, 10);
        bellGbc.weightx = 1.0;
        bellGbc.weighty = 1.0;
        mainPanel.add(notificationBell, bellGbc);
        
        // Add main panel to a scroll pane for overflow handling
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.PRIMARY_COLOUR);
        scrollPane.getViewport().setBackground(UiConstants.PRIMARY_COLOUR);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Add scroll pane to view with proper resizing
        GridBagConstraints viewGbc = new GridBagConstraints();
        viewGbc.fill = GridBagConstraints.BOTH;
        viewGbc.weightx = 1.0;
        viewGbc.weighty = 1.0;
        this.add(scrollPane, viewGbc);
    }

    private JPanel createTopSection() {
        JPanel topSection = new JPanel(new GridBagLayout());
        topSection.setBackground(new Color(245, 245, 245)); // Light gray instead of white
        topSection.setOpaque(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Welcome title with modern styling
        JLabel welcomeTitle = new JLabel("Welcome to Fundi!");
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcomeTitle.setForeground(UiConstants.PRIMARY_COLOUR);
        welcomeTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        topSection.add(welcomeTitle, gbc);

        gbc.gridy++;
        topSection.add(Box.createVerticalStrut(20), gbc);

        // Username display with modern styling
        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.DARK_GRAY);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            usernameLabel.setText("Logged in as: " + mainState.getUsername());
        });
        gbc.gridy++;
        topSection.add(usernameLabel, gbc);

        gbc.gridy++;
        topSection.add(Box.createVerticalStrut(15), gbc);

        // Search section with modern styling
        JPanel searchPanel = createModernSearchPanel();
        gbc.gridy++;
        topSection.add(searchPanel, gbc);

        return topSection;
    }

    private JPanel createModernSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(245, 245, 245)); // Light gray instead of white
        searchPanel.setOpaque(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search field with modern styling
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        searchField.setBackground(Color.WHITE);
        searchField.setMinimumSize(new Dimension(200, 36));
        searchField.setPreferredSize(new Dimension(250, 36));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        searchPanel.add(searchField, gbc);

        // Search button with modern styling
        JButton searchButton = ButtonFactory.createPrimaryButton("Search");
        searchButton.setPreferredSize(new Dimension(80, 36));
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(5, 10, 5, 5);
        searchPanel.add(searchButton, gbc);

        // Wire up search functionality
        Runnable doSearch = () -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                this.searchController.execute(query);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search query.");
            }
        };
        searchButton.addActionListener(evt -> doSearch.run());
        searchField.addActionListener(evt -> doSearch.run());

        return searchPanel;
    }

    private JPanel createCenterSection() {
        JPanel centerSection = new JPanel(new GridBagLayout());
        centerSection.setBackground(new Color(245, 245, 245)); // Light gray instead of white
        centerSection.setOpaque(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Prompt label with modern styling
        JLabel promptLabel = new JLabel("What would you like to look at?");
        promptLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        promptLabel.setForeground(Color.DARK_GRAY);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerSection.add(promptLabel, gbc);

        gbc.gridy++;
        centerSection.add(Box.createVerticalStrut(20), gbc);

        // Create modern button grid
        String[] useCases = { "Portfolios", "News", "Watchlist", "Leaderboard" };
        
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        int col = 0;
        int row = 2; // Start after prompt and spacing
        
        for (String useCase : useCases) {
            JButton useCaseButton = createModernUseCaseButton(useCase);
            
            gbc.gridx = col;
            gbc.gridy = row;
            centerSection.add(useCaseButton, gbc);
            
            col++;
            if (col >= 2) { // Two buttons per row
                col = 0;
                row++;
            }
        }

        return centerSection;
    }

    private JButton createModernUseCaseButton(String useCase) {
        JButton button = new JButton(useCase);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(UiConstants.PRIMARY_COLOUR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        button.setPreferredSize(new Dimension(140, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(UiConstants.PRESSED_COLOUR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(UiConstants.PRIMARY_COLOUR);
            }
        });

        // Wire up navigation logic (keeping original functionality)
        button.addActionListener(evt -> {
            MainState mainState = mainViewModel.getState();
            mainState.setUseCase(useCase);
            mainViewModel.setState(mainState);
            this.navigationController.navigateTo(mainViewModel.getViewName(), useCase.toLowerCase());
            switch (useCase) {
                case "Portfolios" -> this.portfolioHubController.execute(mainState.getUsername());
                case "News" -> this.newsController.execute(mainState.getUsername());
                case "Watchlist" -> {
                    // Defer data fetching until Watchlist is clicked
                    // No-op here; Watchlist view will load data on show
                }
            }
        });

        return button;
    }

    // Removed createBottomSection; notification bell is now added directly to mainPanel

    private JButton createModernNotificationButton() {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Create the main button content
        JLabel iconLabel = new JLabel("🔔"); // Bell emoji as icon
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        iconLabel.setForeground(UiConstants.PRIMARY_COLOUR);
        button.add(iconLabel, BorderLayout.CENTER);

        // Create notification badge (modern styling)
        JLabel badge = new JLabel("0");
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setBackground(Color.RED);
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        badge.setVisible(false); // Hidden by default
        button.add(badge, BorderLayout.EAST);

        // Add modern hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        
        // Update the instance variable to point to this modern button
        // so the increment/clear methods still work
        this.notificationButton = button;

        return button;
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
