package view.tabbedmain;

import interfaceadapter.dashboard.DashboardController;
import interfaceadapter.main.MainState;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.news.NewsController;
import interfaceadapter.portfolio_hub.PortfolioHubController;
import view.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TabbedPaneManager {
    private final JTabbedPane tabbedPane;
    private final Map<Integer, Runnable> tabActions;

    public TabbedPaneManager(
            MainViewModel mainViewModel,
            DashboardController dashboardController,
            PortfolioHubController portfolioHubController,
            NewsController newsController,
            JPanel dashboardView,
            JPanel portfoliosView,
            JPanel newsView,
            JPanel watchlistView,
            JPanel leaderboardView,
            JPanel settingsView) {
        this.tabbedPane = createTabbedPane(dashboardView, portfoliosView, newsView,
                watchlistView, leaderboardView, settingsView);

        // Create tab actions map
        this.tabActions = new HashMap<>();
        this.tabActions.put(0, () -> {
            String username = mainViewModel.getState().getUsername();
            if (username != null && !username.isEmpty()) {
                dashboardController.execute(username);
            }
        });

        this.tabActions.put(1, () -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null) {
                portfolioHubController.execute(mainState.getUsername());
            }
        });

        this.tabActions.put(2, () -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null) {
                newsController.execute(mainState.getUsername());
            }
        });

        // Add change listener to handle tab switching
        this.tabbedPane.addChangeListener(e -> {
            int selectedIndex = this.tabbedPane.getSelectedIndex();
            Runnable action = tabActions.get(selectedIndex);
            if (action != null) {
                action.run();
            }
        });
    }

    private JTabbedPane createTabbedPane(JPanel dashboardView, JPanel portfoliosView,
            JPanel newsView, JPanel watchlistView,
            JPanel leaderboardView, JPanel settingsView) {
        JTabbedPane pane = new JTabbedPane();

        // Style the tabbed pane
        pane.setBackground(UiConstants.Colors.PRIMARY);
        pane.setForeground(UiConstants.Colors.ON_PRIMARY);
        pane.setFont(new Font("Sans Serif", Font.BOLD, 14));

        // Add tabs
        pane.addTab("Dashboard", dashboardView);
        pane.addTab("Portfolios", portfoliosView);
        pane.addTab("News", newsView);
        pane.addTab("Watchlist", watchlistView);
        pane.addTab("Leaderboard", leaderboardView);
        pane.addTab("Settings", settingsView);

        return pane;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setSelectedTab(int index) {
        if (index >= 0 && index < tabbedPane.getTabCount()) {
            tabbedPane.setSelectedIndex(index);
        }
    }
}
