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
import view.ui.UiConstants;
import view.ui.icons.BellIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabbedMainView extends BaseView {
    private final MainViewModel mainViewModel;
    private final PortfolioHubController portfolioHubController;
    private final NewsController newsController;
    @SuppressWarnings("unused")
    private final PortfolioController portfolioController;
    @SuppressWarnings("unused")
    private final NavigationController navigationController;
    @SuppressWarnings("unused")
    private final SearchController searchController;
    @SuppressWarnings("unused")
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
        // Don't modify the button's layout - keep it simple
        // Just add the badge next to the button instead of inside it

        // Register this view with the notification manager
        NotificationManager.getInstance().setMainView(this);

        // Use BaseView's content area instead of replacing the whole page
        content.setLayout(new BorderLayout());

        // Add notification to header right - simpler layout
        JPanel headerRight = new JPanel();
        headerRight.setLayout(new OverlayLayout(headerRight));
        headerRight.setOpaque(false);
        
        // Add button first (bottom layer)
        notificationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        notificationButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        headerRight.add(notificationButton);
        
        // Add badge on top, positioned in top-right corner
        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        badgePanel.setOpaque(false);
        badgePanel.add(notificationBadge);
        badgePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        badgePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        headerRight.add(badgePanel);
        
        header.add(headerRight, BorderLayout.EAST);

        // Create tabbed pane
        tabbedPane = createTabbedPane();
        content.add(tabbedPane, BorderLayout.CENTER);

        // Initialize with portfolios data when view is created
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                portfolioHubController.execute(mainState.getUsername());
            }
        });
    }

    private JButton createNotificationButton() {
        JButton button = new JButton();
        button.setIcon(new BellIcon(18, UiConstants.Colors.ON_PRIMARY));
        button.setText(null);
        button.setForeground(UiConstants.Colors.ON_PRIMARY);
        button.setBackground(UiConstants.Colors.PRIMARY);
        button.setPreferredSize(new Dimension(32, 32));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY.darker(), 2),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change hover color based on notification state
                if (notificationCount > 0) {
                    button.setBackground(UiConstants.Colors.DANGER.darker());
                } else {
                    button.setBackground(UiConstants.Colors.PRIMARY.brighter());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restore color based on notification state
                if (notificationCount > 0) {
                    button.setBackground(UiConstants.Colors.DANGER);
                } else {
                    button.setBackground(UiConstants.Colors.PRIMARY);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (notificationCount > 0) {
                    button.setBackground(UiConstants.Colors.DANGER.darker());
                } else {
                    button.setBackground(UiConstants.Colors.SECONDARY);
                }
            }
        });

        // Add click action
        button.addActionListener(e -> showNotificationDialog());

        return button;
    }

    private JLabel createNotificationBadge() {
        JLabel badge = new JLabel("0");
        badge.setFont(new Font("Sans Serif", Font.BOLD, 9));
        badge.setForeground(UiConstants.Colors.ON_PRIMARY);
        badge.setBackground(UiConstants.Colors.DANGER);
        badge.setOpaque(true);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setVerticalAlignment(SwingConstants.CENTER);
        badge.setPreferredSize(new Dimension(16, 16));
        badge.setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3));
        badge.setVisible(false); // Hidden by default

        return badge;
    }

    private void showNotificationDialog() {
        java.util.List<String> notifications = NotificationManager.getInstance().getNotifications();

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
        headerPanel.setBackground(UiConstants.Colors.PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("📢 Notifications");
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 18));
        titleLabel.setForeground(UiConstants.Colors.ON_PRIMARY);

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
        newsButton.setBackground(UiConstants.Colors.PRIMARY);
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
        clearButton.setBackground(UiConstants.Colors.DANGER);
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
        closeButton.setBorder(BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1));
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
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)));

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
        tabbedPane.setBackground(UiConstants.Colors.PRIMARY);
        tabbedPane.setForeground(UiConstants.Colors.ON_PRIMARY);
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
                case 0 -> { /* Dashboard */ }
                case 1 -> { if (mainState.getUsername() != null) portfolioHubController.execute(mainState.getUsername()); }
                case 2 -> { if (mainState.getUsername() != null) newsController.execute(mainState.getUsername()); }
                case 3 -> { /* Watchlist */ }
                case 4 -> { /* Leaderboard */ }
                default -> {}
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
            notificationButton.setBackground(UiConstants.Colors.DANGER);
            notificationButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UiConstants.Colors.DANGER.darker(), 2),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        } else {
            notificationBadge.setVisible(false);

            // Restore default primary color when no notifications
            notificationButton.setBackground(UiConstants.Colors.PRIMARY);
            notificationButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY.darker(), 2),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        }

        // Repaint to ensure visual update
        notificationButton.repaint();
    }

    public int getNotificationCount() {
        return notificationCount;
    }
}
