package view;

import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolios.PortfoliosController;
import interface_adapter.portfolio.PortfolioController;
import view.components.UIFactory;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;

import javax.swing.*;
import java.awt.*;

public class TabbedMainView extends BaseView {
    private final MainViewModel mainViewModel;
    private final PortfoliosController portfoliosController;
    private final NewsController newsController;
    private final PortfolioController portfolioController;
    private final NavigationController navigationController;
    private final SearchController searchController;
    private final SearchViewModel searchViewModel;

    private final PortfoliosView portfoliosView;
    private final NewsView newsView;
    private final WatchlistView watchlistView;
    private final LeaderboardView leaderboardView;

    private final JTabbedPane tabbedPane;

    public TabbedMainView(MainViewModel mainViewModel,
            PortfoliosController portfoliosController,
            NewsController newsController,
            PortfolioController portfolioController,
            NavigationController navigationController,
            SearchController searchController,
            SearchViewModel searchViewModel,
            PortfoliosView portfoliosView,
            NewsView newsView,
            WatchlistView watchlistView,
            LeaderboardView leaderboardView) {
        super("tabbedmain");
        this.mainViewModel = mainViewModel;
        this.portfoliosController = portfoliosController;
        this.newsController = newsController;
        this.portfolioController = portfolioController;
        this.navigationController = navigationController;
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        this.portfoliosView = portfoliosView;
        this.newsView = newsView;
        this.watchlistView = watchlistView;
        this.leaderboardView = leaderboardView;

        JPanel contentPanel = createGradientContentPanel();
        this.add(contentPanel, BorderLayout.CENTER);

        // Create top panel with welcome, search, and username
        JPanel topPanel = createTopPanel();
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Create tabbed pane
        tabbedPane = createTabbedPane();
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Initialize with portfolios data when view is created
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                portfoliosController.execute(mainState.getUsername());
            }
        });
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        // Welcome panel with settings button
        JPanel welcomePanel = UIFactory.createTitlePanel("Welcome to Fundi!");
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
        JButton searchButton = UIFactory.createStyledButton("Search");
        JTextField searchField = UIFactory.createTextField();
        JPanel searchPanel = UIFactory.createSingleFieldForm(searchField, searchButton);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        mainViewModel.addPropertyChangeListener(evt -> {
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
        topPanel.add(Box.createVerticalStrut(15));

        // Wire up search functionality
        Runnable doSearch = () -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                searchController.execute(query);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search query.");
            }
        };
        searchButton.addActionListener(e -> doSearch.run());
        searchField.addActionListener(e -> doSearch.run());

        return topPanel;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Style the tabbed pane
        tabbedPane.setBackground(new Color(30, 60, 120));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("Sans Serif", Font.BOLD, 14));

        // Add tabs
        tabbedPane.addTab("Portfolios", portfoliosView);
        tabbedPane.addTab("News", newsView);
        tabbedPane.addTab("Watchlist", watchlistView);
        tabbedPane.addTab("Leaderboard", leaderboardView);

        // Add change listener to handle tab switching
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            MainState mainState = mainViewModel.getState();

            switch (selectedIndex) {
                case 0: // Portfolios
                    if (mainState.getUsername() != null) {
                        portfoliosController.execute(mainState.getUsername());
                    }
                    break;
                case 1: // News
                    if (mainState.getUsername() != null) {
                        newsController.execute(mainState.getUsername());
                    }
                    break;
                case 2: // Watchlist
                    // Future implementation
                    break;
                case 3: // Leaderboard
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
