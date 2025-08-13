package view;

import java.awt.*;

import interfaceadapter.dashboard.DashboardController;
import interfaceadapter.main.MainState;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.navigation.NavigationController;
import interfaceadapter.news.NewsController;
import interfaceadapter.portfolio.PortfolioController;
import interfaceadapter.portfolio_hub.PortfolioHubController;
import interfaceadapter.search.SearchController;
import interfaceadapter.search.SearchViewModel;
import usecase.notifications.*;
import view.tabbedmain.NotificationPanel;
import view.tabbedmain.TabbedPaneManager;

public class TabbedMainView extends AbstractBaseView {
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
        getContent().setLayout(new BorderLayout());

        // Add notification panel to header
        getHeader().add(notificationPanel, BorderLayout.EAST);

        // Add tabbed pane to content
        getContent().add(tabbedPaneManager.getTabbedPane(), BorderLayout.CENTER);

        // Initialize with portfolios data when view is created
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                portfolioHubController.execute(mainState.getUsername());
            }
        });
    }

    /**
     * Sets the selected tab in the tabbed pane to the tab at the specified index.
     * Delegates the tab-switch operation to the TabbedPaneManager.
     *
     * @param index the index of the tab to select (0-based)
     */
    public void setSelectedTab(int index) {
        tabbedPaneManager.setSelectedTab(index);
    }

    /**
     * Adds a new notification via the notification panel.
     */
    public void addNotification() {
        notificationPanel.addNotification();
    }

    /**
     * Clears all notifications from the notification panel.
     */
    public void clearNotifications() {
        notificationPanel.clearNotifications();
    }

    /**
     * Returns the current number of notifications tracked by the notification
     * panel.
     *
     * @return the notification count
     */
    public int getNotificationCount() {
        return notificationPanel.getNotificationCount();
    }
}
