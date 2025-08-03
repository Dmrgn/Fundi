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

    private final JTabbedPane tabbedPane;

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
            LeaderboardView leaderboardView) {
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

        JPanel contentPanel = createGradientContentPanel();
        this.add(contentPanel, BorderLayout.CENTER);

        // Create tabbed pane (no top panel needed now)
        tabbedPane = createTabbedPane();
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Initialize with portfolios data when view is created
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                portfolioHubController.execute(mainState.getUsername());
            }
        });
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
}
