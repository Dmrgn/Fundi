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
import view.ui.icons.BellIcon;

import javax.swing.*;
import java.awt.*;
import view.ui.FieldFactory;

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

    public MainView(MainViewModel mainViewModel, PortfolioHubController portfolioHubController,
            NewsController newsController, NavigationController navigationController, SearchController searchController,
            SearchViewModel searchViewModel) {
        super("main");
        this.mainViewModel = mainViewModel;
        this.portfolioHubController = portfolioHubController;
        this.newsController = newsController;
        this.navigationController = navigationController;
        this.searchController = searchController;
        // Note: searchViewModel parameter is kept for API compatibility but not stored

        // Header: back/title left, notification bell right
        JLabel title = new JLabel("Home");
        title.setFont(UiConstants.Fonts.TITLE);
        title.setForeground(UiConstants.Colors.ON_PRIMARY);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(title);
        header.add(headerLeft, BorderLayout.WEST);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerRight.setOpaque(false);
        this.notificationButton = createModernNotificationButton();
        headerRight.add(this.notificationButton);
        header.add(headerRight, BorderLayout.EAST);

        // Content: top section (welcome + username + search), center (quick nav)
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM);
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.anchor = GridBagConstraints.CENTER;
        mainGbc.weightx = 1.0;

        JPanel topSection = createTopSection();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.gridwidth = 1;
        mainPanel.add(topSection, mainGbc);

        mainGbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.XL), mainGbc);

        JPanel centerSection = createCenterSection();
        mainGbc.gridy++;
        mainPanel.add(centerSection, mainGbc);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createTopSection() {
        JPanel topSection = new JPanel(new GridBagLayout());
        topSection.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel welcomeTitle = new JLabel("Welcome to Fundi!");
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcomeTitle.setForeground(UiConstants.Colors.ON_PRIMARY);
        welcomeTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        topSection.add(welcomeTitle, gbc);

        gbc.gridy++;
        topSection.add(Box.createVerticalStrut(UiConstants.Spacing.LG), gbc);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(UiConstants.Fonts.BODY);
        usernameLabel.setForeground(UiConstants.Colors.SURFACE_BG);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            usernameLabel.setText("Logged in as: " + mainState.getUsername());
        });
        gbc.gridy++;
        topSection.add(usernameLabel, gbc);

        gbc.gridy++;
        topSection.add(Box.createVerticalStrut(UiConstants.Spacing.MD), gbc);

        JPanel searchPanel = createModernSearchPanel();
        gbc.gridy++;
        topSection.add(searchPanel, gbc);

        return topSection;
    }

    private JPanel createModernSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField searchField = FieldFactory.createSearchField("Search symbols or companies");
        searchField.setMinimumSize(new Dimension(200, 36));
        searchField.setPreferredSize(new Dimension(250, 36));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        searchPanel.add(searchField, gbc);

        JButton searchButton = ButtonFactory.createPrimaryButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 36));
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.LG, UiConstants.Spacing.SM, UiConstants.Spacing.SM);
        searchPanel.add(searchButton, gbc);

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
        centerSection.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.MD, UiConstants.Spacing.MD, UiConstants.Spacing.MD, UiConstants.Spacing.MD);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel promptLabel = new JLabel("What would you like to look at?");
        promptLabel.setFont(UiConstants.Fonts.BODY);
        promptLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerSection.add(promptLabel, gbc);

        gbc.gridy++;
        centerSection.add(Box.createVerticalStrut(UiConstants.Spacing.XL), gbc);

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
        JButton button = ButtonFactory.createPrimaryButton(useCase);
        button.setPreferredSize(new Dimension(140, 50));

        // Wire up navigation logic
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
                }
            }
        });

        return button;
    }

    private JButton createModernNotificationButton() {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBackground(UiConstants.Colors.SURFACE_BG);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new BellIcon(18, UiConstants.Colors.PRIMARY));
        iconLabel.setForeground(UiConstants.Colors.PRIMARY);
        button.add(iconLabel, BorderLayout.CENTER);

        JLabel badge = new JLabel("0");
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
        badge.setForeground(UiConstants.Colors.ON_PRIMARY);
        badge.setBackground(UiConstants.Colors.BADGE_BG);
        badge.setOpaque(true);
        badge.setBorder(UiConstants.BADGE_BORDER);
        badge.setVisible(false);
        button.add(badge, BorderLayout.EAST);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(UiConstants.Colors.SURFACE_BG);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(UiConstants.Colors.SURFACE_BG);
            }
        });

        // Add click action for notifications
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Notifications: " + notificationCount + " new notifications", 
                "Notifications", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        this.notificationButton = button;
        return button;
    }

    // Notification helpers
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
