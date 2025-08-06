package view;

import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolio_hub.PortfolioHubController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabbedMainView extends BaseView {
    private final MainViewModel mainViewModel;
    private final PortfolioHubController portfolioHubController;
    private final NewsController newsController;
    private final PortfolioController portfolioController;
    private final NavigationController navigationController;
    private final SearchController searchController;
    private final SearchViewModel searchViewModel;

    private final DashboardView dashboardView;
    private final PortfolioHubView portfoliosView;
    private final NewsView newsView;
    private final WatchlistView watchlistView;
    private final LeaderboardView leaderboardView;
    private final SettingsView settingsView;

    private final JTabbedPane tabbedPane;
    private final JButton notificationButton;
    private final JLabel notificationBadge;
    private int notificationCount = 0;

    public TabbedMainView(MainViewModel mainViewModel,
                          PortfolioHubController portfolioHubController,
                          NewsController newsController,
                          PortfolioController portfolioController,
                          NavigationController navigationController,
                          SearchController searchController,
                          SearchViewModel searchViewModel,
                          DashboardView dashboardView,
                          PortfolioHubView portfoliosView,
                          NewsView newsView,
                          WatchlistView watchlistView,
                          LeaderboardView leaderboardView, SettingsView settingsView) {
        super("tabbedmain");
        this.mainViewModel = mainViewModel;
        this.portfolioHubController = portfolioHubController;
        this.newsController = newsController;
        this.portfolioController = portfolioController;
        this.navigationController = navigationController;
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        this.dashboardView = dashboardView;
        this.portfoliosView = portfoliosView;
        this.newsView = newsView;
        this.watchlistView = watchlistView;
        this.leaderboardView = leaderboardView;
        this.settingsView = settingsView;

        // Initialize notification components
        this.notificationButton = createNotificationButton();
        this.notificationBadge = createNotificationBadge();

        JPanel contentPanel = createGradientContentPanel();
        this.add(contentPanel, BorderLayout.CENTER);

        // Create tabbed pane
        tabbedPane = createTabbedPane();
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add notification UI to bottom-right
        JPanel bottomPanel = createBottomPanel();
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Initialize with portfolios data when view is created
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                portfolioHubController.execute(mainState.getUsername());
            }
        });
    }

    private JButton createNotificationButton() {
        JButton button = new JButton("🔔");
        button.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 60, 120));
        button.setPreferredSize(new Dimension(50, 50));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 70, 130), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(40, 70, 130));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(30, 60, 120));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(25, 50, 100));
            }
        });

        // Add click action
        button.addActionListener(e -> showNotificationDialog());

        return button;
    }

    private JLabel createNotificationBadge() {
        JLabel badge = new JLabel("0");
        badge.setFont(new Font("Sans Serif", Font.BOLD, 10));
        badge.setForeground(Color.WHITE);
        badge.setBackground(new Color(220, 20, 60));
        badge.setOpaque(true);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setVerticalAlignment(SwingConstants.CENTER);
        badge.setPreferredSize(new Dimension(18, 18));
        badge.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        badge.setVisible(false); // Hidden by default

        return badge;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new OverlayLayout(bottomPanel));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 20));

        // Container for notification button positioned in bottom-right
        JPanel notificationContainer = new JPanel();
        notificationContainer.setLayout(new OverlayLayout(notificationContainer));
        notificationContainer.setOpaque(false);
        notificationContainer.setAlignmentX(1.0f); // Right align
        notificationContainer.setAlignmentY(1.0f); // Bottom align

        // Add button to container
        notificationButton.setAlignmentX(0.5f);
        notificationButton.setAlignmentY(0.5f);
        notificationContainer.add(notificationButton);

        // Position badge in top-right corner of button
        notificationBadge.setAlignmentX(0.85f);
        notificationBadge.setAlignmentY(0.15f);
        notificationContainer.add(notificationBadge);

        // Panel to push notification to bottom-right
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        spacerPanel.setAlignmentX(0.0f);
        spacerPanel.setAlignmentY(0.0f);

        bottomPanel.add(spacerPanel);
        bottomPanel.add(notificationContainer);

        return bottomPanel;
    }

    private void showNotificationDialog() {
        String message = notificationCount == 0 ? 
            "No new notifications" : 
            "You have " + notificationCount + " notification(s)";

        String title = "Notifications";
        int messageType = notificationCount == 0 ? 
            JOptionPane.INFORMATION_MESSAGE : 
            JOptionPane.INFORMATION_MESSAGE;

        JOptionPane.showMessageDialog(this, message, title, messageType);
        
        // Clear notifications after viewing
        if (notificationCount > 0) {
            clearNotifications();
        }
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Style the tabbed pane
        tabbedPane.setBackground(new Color(30, 60, 120));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("Sans Serif", Font.BOLD, 14));

        // Add tabs with Dashboard first
        tabbedPane.addTab("Dashboard", dashboardView);
        tabbedPane.addTab("Portfolios", portfoliosView);
        tabbedPane.addTab("News", newsView);
        tabbedPane.addTab("Watchlist", watchlistView);
        tabbedPane.addTab("Leaderboard", leaderboardView);
        tabbedPane.addTab("Settings", settingsView);

        // Add change listener to handle tab switching
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            MainState mainState = mainViewModel.getState();

            switch (selectedIndex) {
                case 0: // Dashboard
                    // Dashboard is always available, no special action needed
                    break;
                case 1: // Portfolios
                    if (mainState.getUsername() != null) {
                        portfolioHubController.execute(mainState.getUsername());
                    }
                    break;
                case 2: // News
                    if (mainState.getUsername() != null) {
                        newsController.execute(mainState.getUsername());
                    }
                    break;
                case 3: // Watchlist
                    // Future implementation
                    break;
                case 4: // Leaderboard
                    // Future implementation
                    break;
            }
        });

        return tabbedPane;
    }

    public void setSelectedTab(int index) {
        if (index >= 0 && index < tabbedPane.getTabCount()) {
            tabbedPane.setSelectedIndex(index);
        }
    }

    // Notification control methods
    public void addNotification() {
        notificationCount++;
        updateNotificationBadge();
    }

    public void clearNotifications() {
        notificationCount = 0;
        updateNotificationBadge();
    }

    private void updateNotificationBadge() {
        if (notificationCount > 0) {
            notificationBadge.setText(String.valueOf(notificationCount));
            notificationBadge.setVisible(true);
        } else {
            notificationBadge.setVisible(false);
        }
    }

    public int getNotificationCount() {
        return notificationCount;
    }
}
