package view;

import interfaceadapter.dashboard.DashboardController;
import interfaceadapter.main.MainState;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.news.NewsController;
import interfaceadapter.portfolio_hub.PortfolioHubController;
import interfaceadapter.portfolio.PortfolioController;
import interfaceadapter.navigation.NavigationController;
import interfaceadapter.search.SearchController;
import interfaceadapter.search.SearchViewModel;
import usecase.notifications.*;
import view.tabbedmain.NotificationPanel;
import view.tabbedmain.TabbedPaneManager;

import java.awt.*;

public class TabbedMainView extends BaseView {
    @SuppressWarnings("unused")
    private final MainViewModel mainViewModel;
    private final NotificationPanel notificationPanel;
    private final TabbedPaneManager tabbedPaneManager;

    public TabbedMainView(MainViewModel mainViewModel,
            PortfolioHubController portfolioHubController,
            DashboardController dashboardController,
            NewsController newsController,
            PortfolioController portfolioController,
            NavigationController navigationController,
            SearchController searchController,
            SearchViewModel searchViewModel,
            DashboardView dashboardView,
            PortfolioHubView portfoliosView,
            NewsView newsView,
            WatchlistView watchlistView,
            LeaderboardView leaderboardView,
            SettingsView settingsView) {
        super("tabbedmain");
        this.mainViewModel = mainViewModel;

        // Initialize components
        this.notificationPanel = new NotificationPanel();
        this.tabbedPaneManager = new TabbedPaneManager(
                mainViewModel, dashboardController, portfolioHubController, newsController,
                dashboardView, portfoliosView, newsView, watchlistView, leaderboardView, settingsView);

        // Set up tab switching callback
        this.notificationPanel.setTabSwitcher(this.tabbedPaneManager::setSelectedTab);

        // Register this view with the notification manager
        NotificationManager.getInstance().setMainView(this);

        // Use BaseView's content area
        content.setLayout(new BorderLayout());

        // Add notification panel to header
        header.add(notificationPanel, BorderLayout.EAST);

        // Add tabbed pane to content
        content.add(tabbedPaneManager.getTabbedPane(), BorderLayout.CENTER);

        // Initialize with portfolios data when view is created
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                portfolioHubController.execute(mainState.getUsername());
            }
        });
    }

    public void setSelectedTab(int index) {
        tabbedPaneManager.setSelectedTab(index);
    }

    // Notification control methods - delegate to NotificationPanel
    public void addNotification() {
        notificationPanel.addNotification();
    }

    public void clearNotifications() {
        notificationPanel.clearNotifications();
    }

    public int getNotificationCount() {
        return notificationPanel.getNotificationCount();
    }
}
