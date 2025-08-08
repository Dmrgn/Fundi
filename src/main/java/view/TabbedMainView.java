package view;

import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolio_hub.PortfolioHubController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import use_case.notifications.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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

        // Register this view with the notification manager
        NotificationManager.getInstance().setMainView(this);

        JPanel contentPanel = createGradientContentPanel();
        this.add(contentPanel, BorderLayout.CENTER);

        // Ensure the content panel spans the full width and height of the Base View
        contentPanel.setLayout(new BorderLayout());

        // Create tabbed pane
        tabbedPane = createTabbedPane();
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add notification button to the top panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(true);
        rightPanel.setBackground(new Color(240, 240, 240)); // Light gray color

        rightPanel.add(notificationButton, BorderLayout.NORTH);

        contentPanel.add(rightPanel, BorderLayout.EAST);

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
        button.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 60, 120)); // Default blue color
        button.setPreferredSize(new Dimension(30, 32));
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
                // Change hover color based on notification state
                if (notificationCount > 0) {
                    button.setBackground(new Color(200, 50, 50)); // Darker red on hover
                } else {
                    button.setBackground(new Color(40, 70, 130)); // Blue on hover
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restore color based on notification state
                if (notificationCount > 0) {
                    button.setBackground(new Color(220, 20, 60)); // Red when notifications exist
                } else {
                    button.setBackground(new Color(30, 60, 120)); // Default blue
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (notificationCount > 0) {
                    button.setBackground(new Color(180, 30, 30)); // Darker red on press
                } else {
                    button.setBackground(new Color(25, 50, 100)); // Darker blue on press
                }
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

    private void showNotificationDialog() {
        List<String> notifications = NotificationManager.getInstance().getNotifications();
        
        if (notifications.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No new notifications", "Notifications", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create custom dialog to show notifications
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Notifications", true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 60, 120));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("📢 Notifications");
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel countLabel = new JLabel(notifications.size() + " notification(s)");
        countLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        countLabel.setForeground(new Color(200, 200, 200));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(countLabel, BorderLayout.EAST);

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (String notification : notifications) {
            JPanel notifPanel = createNotificationItem(notification);
            contentPanel.add(notifPanel);
            contentPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        JButton newsButton = new JButton("Open News");
        newsButton.setFont(new Font("Sans Serif", Font.BOLD, 12));
        newsButton.setForeground(Color.WHITE);
        newsButton.setBackground(new Color(30, 60, 120));
        newsButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        newsButton.setFocusPainted(false);
        newsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newsButton.addActionListener(e -> {
            dialog.dispose();
            setSelectedTab(2); // Switch to News tab
        });

        JButton clearButton = new JButton("Clear All");
        clearButton.setFont(new Font("Sans Serif", Font.BOLD, 12));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        clearButton.setFocusPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> {
            NotificationManager.getInstance().clearNotifications();
            dialog.dispose();
        });

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        closeButton.setForeground(new Color(100, 100, 100));
        closeButton.setBackground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(newsButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(closeButton);

        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createNotificationItem(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        JLabel messageLabel = new JLabel("<html><div style='width:400px'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(60, 60, 60));

        JLabel timeLabel = new JLabel("Just now");
        timeLabel.setFont(new Font("Sans Serif", Font.ITALIC, 11));
        timeLabel.setForeground(new Color(150, 150, 150));

        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(timeLabel, BorderLayout.EAST);

        return panel;
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
            
            // Change button color to red when notifications are available
            notificationButton.setBackground(new Color(220, 20, 60)); // Red color
            notificationButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 50, 50), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        } else {
            notificationBadge.setVisible(false);
            
            // Restore default blue color when no notifications
            notificationButton.setBackground(new Color(30, 60, 120)); // Default blue
            notificationButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 70, 130), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }
        
        // Repaint to ensure visual update
        notificationButton.repaint();
    }

    public int getNotificationCount() {
        return notificationCount;
    }
}
