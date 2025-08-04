package view;

import entity.PortfolioValuePoint;
import entity.SearchResult;
import interface_adapter.company_details.CompanyDetailsController;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import view.components.UiFactory;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class DashboardView extends BaseView {
    private final MainViewModel mainViewModel;
    private final SearchController searchController;
    private final SearchViewModel searchViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final DashboardController dashboardController;
    private final NavigationController navigationController;
    private final CompanyDetailsController companyDetailsController;
    private ChartPanel chartPanel;
    private JPanel resultsPanel; // Keep reference to results panel for back button functionality
    private JPanel backButtonPanel; // Reference for top-left back button

    public DashboardView(MainViewModel mainViewModel, SearchController searchController,
            SearchViewModel searchViewModel, DashboardViewModel dashboardViewModel,
            DashboardController dashboardController, NavigationController navigationController,
            CompanyDetailsController companyDetailsController) {
        super("dashboard");
        this.mainViewModel = mainViewModel;
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        this.dashboardViewModel = dashboardViewModel;
        this.dashboardController = dashboardController;
        this.navigationController = navigationController;
        this.companyDetailsController = companyDetailsController;

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BorderLayout());

        // Create back button panel (top-left corner)
        backButtonPanel = createBackButtonPanel(e -> {
            // Clear search results and hide results panel
            var searchState = searchViewModel.getState();
            searchState.setSearchResults(null);
            searchState.setSearchError(null);
            searchState.setQuery("");
            searchViewModel.setState(searchState);
            searchViewModel.firePropertyChanged();
        });
        backButtonPanel.setVisible(false);
        // Use left-aligned FlowLayout for top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setOpaque(false);
        topPanel.add(backButtonPanel);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Wrap dashboard content in a separate panel
        JPanel dashboardContent = new JPanel();
        dashboardContent.setLayout(new BoxLayout(dashboardContent, BoxLayout.Y_AXIS));
        dashboardContent.setOpaque(false);
        dashboardContent.add(createWelcomePanel());
        dashboardContent.add(Box.createVerticalStrut(20));
        dashboardContent.add(createSearchPanel());
        dashboardContent.add(Box.createVerticalStrut(20));
        dashboardContent.add(createPortfolioChart());
        dashboardContent.add(Box.createVerticalStrut(20));
        dashboardContent.add(createUsernamePanel());
        dashboardContent.add(Box.createVerticalGlue());

        contentPanel.add(dashboardContent, BorderLayout.CENTER);

        this.add(contentPanel, BorderLayout.CENTER);

        // Set up listeners
        setupListeners();
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = UiFactory.createTitlePanel("Welcome to Fundi!");

        // Add settings button
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

        return welcomePanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchContainer = new JPanel();
        searchContainer.setLayout(new BoxLayout(searchContainer, BoxLayout.Y_AXIS));
        searchContainer.setOpaque(false);

        // Search title
        JLabel searchTitle = new JLabel("Search Stocks");
        searchTitle.setFont(new Font("Sans Serif", Font.BOLD, 18));
        searchTitle.setForeground(Color.WHITE);
        searchTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Search components
        JButton searchButton = UiFactory.createStyledButton("Search");
        JTextField searchField = UiFactory.createTextField();
        JPanel searchPanel = UiFactory.createSingleFieldForm(searchField, searchButton);

        // Create results area
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);
        resultsPanel.setVisible(false); // Initially hidden

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

        // Listen for search results
        searchViewModel.addPropertyChangeListener(evt -> {
            var searchState = searchViewModel.getState();

            // Clear previous results
            resultsPanel.removeAll();

            if (searchState.getSearchError() != null) {
                // Show error and back button
                JLabel errorLabel = new JLabel("Error: " + searchState.getSearchError());
                errorLabel.setForeground(Color.RED);
                errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(errorLabel);
                resultsPanel.setVisible(true);
                backButtonPanel.setVisible(true);
            } else if (searchState.getSearchResults() != null && !searchState.getSearchResults().isEmpty()) {
                // Show results and back button
                JLabel resultsTitle = new JLabel("Search Results:");
                resultsTitle.setFont(new Font("Sans Serif", Font.BOLD, 14));
                resultsTitle.setForeground(Color.WHITE);
                resultsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(resultsTitle);
                resultsPanel.add(Box.createVerticalStrut(10));

                for (var result : searchState.getSearchResults()) {
                    JPanel resultItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
                    resultItem.setOpaque(false);
                    resultItem.setMaximumSize(new Dimension(600, 35));
                    resultItem.setPreferredSize(new Dimension(600, 35));
                    resultItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    resultItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                    // Make the result item clickable
                    resultItem.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                            // Trigger company details fetch and update navigation stack
                            companyDetailsController.execute(result.getSymbol(), "tabbedmain");
                        }

                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent e) {
                            resultItem.setOpaque(true);
                            resultItem.setBackground(new Color(70, 100, 150, 180));
                            resultItem.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
                            resultItem.revalidate();
                            resultItem.repaint();
                        }

                        @Override
                        public void mouseExited(java.awt.event.MouseEvent e) {
                            resultItem.setOpaque(false);
                            resultItem.setBackground(null);
                            resultItem.setBorder(null);
                            resultItem.revalidate();
                            resultItem.repaint();
                        }
                    });

                    JLabel symbolLabel = new JLabel(result.getSymbol());
                    symbolLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
                    symbolLabel.setForeground(Color.CYAN);
                    symbolLabel.setPreferredSize(new Dimension(80, 25));

                    JLabel nameLabel = new JLabel(result.getName());
                    nameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 13));
                    nameLabel.setForeground(Color.WHITE);
                    nameLabel.setPreferredSize(new Dimension(300, 25));

                    JLabel typeLabel = new JLabel("(" + result.getType() + ")");
                    typeLabel.setFont(new Font("Sans Serif", Font.ITALIC, 11));
                    typeLabel.setForeground(Color.LIGHT_GRAY);

                    // Add click hint
                    JLabel clickHint = new JLabel("→ Click for details");
                    clickHint.setFont(new Font("Sans Serif", Font.ITALIC, 10));
                    clickHint.setForeground(new Color(100, 149, 237));

                    resultItem.add(symbolLabel);
                    resultItem.add(nameLabel);
                    resultItem.add(typeLabel);
                    resultItem.add(clickHint);

                    resultsPanel.add(resultItem);
                }
                resultsPanel.setVisible(true);
                backButtonPanel.setVisible(true);
            } else {
                // No results or cleared results - hide everything
                resultsPanel.setVisible(false);
                backButtonPanel.setVisible(false);
            }

            resultsPanel.revalidate();
            resultsPanel.repaint();
            backButtonPanel.revalidate();
            backButtonPanel.repaint();
            searchContainer.revalidate();
            searchContainer.repaint();
        });

        searchContainer.add(searchTitle);
        searchContainer.add(Box.createVerticalStrut(10));
        searchContainer.add(searchPanel);
        searchContainer.add(Box.createVerticalStrut(10));
        searchContainer.add(resultsPanel);

        return searchContainer;
    }

    private JPanel createUsernamePanel() {
        JPanel usernameContainer = new JPanel();
        usernameContainer.setLayout(new BoxLayout(usernameContainer, BoxLayout.Y_AXIS));
        usernameContainer.setOpaque(false);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Listen for username updates
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                usernameLabel.setText("Logged in as: " + mainState.getUsername());
            }
        });

        usernameContainer.add(usernameLabel);

        return usernameContainer;
    }

    private JPanel createPortfolioChart() {
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setOpaque(false);
        chartContainer.setMaximumSize(new Dimension(800, 400));
        chartContainer.setPreferredSize(new Dimension(800, 400));

        // Create initial empty chart
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Portfolio Value Over Time (Last 30 Days)",
                "Date",
                "Value ($)",
                dataset,
                true,
                true,
                false);

        // Customize chart appearance
        chart.setBackgroundPaint(new Color(45, 45, 45));
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(60, 60, 60));
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        chartPanel.setBackground(new Color(45, 45, 45));

        chartContainer.add(chartPanel, BorderLayout.CENTER);

        return chartContainer;
    }

    private void setupListeners() {
        // Listen for dashboard data updates
        dashboardViewModel.addPropertyChangeListener(evt -> {
            DashboardState state = dashboardViewModel.getState();
            updateChart(state.getPortfolioValueHistory());
        });

        // Listen for username changes to trigger data loading
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                // Update dashboard state with username
                DashboardState dashboardState = dashboardViewModel.getState();
                dashboardState.setUsername(mainState.getUsername());
                dashboardViewModel.setState(dashboardState);

                // Load portfolio data
                dashboardController.execute(mainState.getUsername());
            }
        });
    }

    private void updateChart(List<PortfolioValuePoint> valuePoints) {
        if (chartPanel == null)
            return;

        // Group data by portfolio
        Map<String, TimeSeries> portfolioSeries = new HashMap<>();

        for (PortfolioValuePoint point : valuePoints) {
            String portfolioName = point.getPortfolioName();

            if (!portfolioSeries.containsKey(portfolioName)) {
                portfolioSeries.put(portfolioName, new TimeSeries(portfolioName));
            }

            TimeSeries series = portfolioSeries.get(portfolioName);
            LocalDate date = point.getDate();
            Date javaDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Day day = new Day(javaDate);

            try {
                series.addOrUpdate(day, point.getValue());
            } catch (Exception e) {
                // Handle duplicate dates by updating the value
                series.update(day, point.getValue());
            }
        }

        // Create new dataset with all portfolio series
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (TimeSeries series : portfolioSeries.values()) {
            dataset.addSeries(series);
        }

        // Update chart
        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(dataset);

        chartPanel.repaint();
    }
}
