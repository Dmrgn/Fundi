package view;

import entity.CurrencyConverter;
import entity.PortfolioValuePoint;
import entity.PreferredCurrencyManager;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search.SearchState;
import interface_adapter.company_details.CompanyDetailsController;
import entity.SearchResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.PanelFactory;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

import static entity.PreferredCurrencyManager.*;

public class DashboardView extends BaseView {
    private final MainViewModel mainViewModel;
    private final SearchController searchController;
    private final SearchViewModel searchViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final DashboardController dashboardController;
    private final interface_adapter.navigation.NavigationController navigationController;
    private final CompanyDetailsController companyDetailsController;
    private ChartPanel chartPanel;
    private JPanel searchResultsPanel;



    public DashboardView(MainViewModel mainViewModel, SearchController searchController,
            SearchViewModel searchViewModel, DashboardViewModel dashboardViewModel,
            DashboardController dashboardController,
            interface_adapter.navigation.NavigationController navigationController,
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
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(contentPanel, BorderLayout.CENTER);

        // Create search results panel
        searchResultsPanel = new JPanel();
        searchResultsPanel.setLayout(new BoxLayout(searchResultsPanel, BoxLayout.Y_AXIS));
        searchResultsPanel.setOpaque(false);

        // Create the dashboard content
        contentPanel.add(createWelcomePanel());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createSearchPanel());
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(searchResultsPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createPortfolioChart());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createUsernamePanel());
        contentPanel.add(Box.createVerticalGlue());

        // Set up listeners
        setupListeners();
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = PanelFactory.createTitlePanel("Welcome to Fundi!");

        // Add settings button
        JButton settingsButton = new JButton();
        try {
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
        JButton searchButton = ButtonFactory.createStyledButton("Search");
        JTextField searchField = FieldFactory.createTextField();

        // Make search field more prominent
        searchField.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setMaximumSize(new Dimension(200, 30));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setOpaque(false);
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10)); // Add spacing between field and button
        searchPanel.add(searchButton);

        // Wire up search functionality
        Runnable doSearch = () -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                searchController.execute(query);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search query.");
            }
        };

        // Ensure both button click and Enter key work
        searchButton.addActionListener(e -> {
            doSearch.run();
            searchField.requestFocus(); // Keep focus on search field
        });
        searchField.addActionListener(e -> doSearch.run());

        searchContainer.add(searchTitle);
        searchContainer.add(Box.createVerticalStrut(10));
        searchContainer.add(searchPanel);

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
                "Value",
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
        // Listen for search results updates
        searchViewModel.addPropertyChangeListener(evt -> {
            SearchState state = searchViewModel.getState();
            updateSearchResults(state.getSearchResults());
        });

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

    private void updateSearchResults(List<SearchResult> results) {
        searchResultsPanel.removeAll();

        if (results != null && !results.isEmpty()) {
            // Create a simple styled container for results
            JPanel resultsContainer = new JPanel(new BorderLayout());
            resultsContainer.setOpaque(false);
            resultsContainer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            resultsContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Header with title and clear button
            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
            headerPanel.setOpaque(false);

            JLabel resultsTitle = new JLabel("Search Results:");
            resultsTitle.setFont(new Font("Sans Serif", Font.BOLD, 16));
            resultsTitle.setForeground(Color.WHITE);

            JButton clearButton = new JButton("✕");
            clearButton.setFont(new Font("Sans Serif", Font.BOLD, 12));
            clearButton.setForeground(Color.WHITE);
            clearButton.setBackground(new Color(180, 50, 50));
            clearButton.setPreferredSize(new Dimension(25, 25));
            clearButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            clearButton.setFocusPainted(false);
            clearButton.setToolTipText("Clear search results");
            clearButton.addActionListener(e -> clearSearchResults());

            headerPanel.add(resultsTitle);
            headerPanel.add(Box.createHorizontalGlue());
            headerPanel.add(clearButton);

            resultsContainer.add(headerPanel, BorderLayout.NORTH);

            // Content panel for results
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setOpaque(false);
            contentPanel.add(Box.createVerticalStrut(10));

            // Filter and add valid results
            List<SearchResult> validResults = results.stream()
                    .filter(result -> result.getSymbol() != null && !result.getSymbol().isEmpty()
                            && result.getName() != null && !result.getName().isEmpty())
                    .limit(10)
                    .toList();

            for (SearchResult result : validResults) {
                JButton resultButton = ButtonFactory.createStyledButton(result.getSymbol() + " - " + result.getName());
                resultButton.setFont(new Font("Sans Serif", Font.PLAIN, 12));
                resultButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultButton.addActionListener(e -> {
                    companyDetailsController.execute(result.getSymbol(), "dashboard");
                });
                contentPanel.add(resultButton);
                contentPanel.add(Box.createVerticalStrut(8));
            }

            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setBorder(null);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(480, 400));

            scrollPane.getVerticalScrollBar().setBackground(new Color(60, 60, 60));
            scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = new Color(100, 100, 100);
                    this.trackColor = new Color(45, 45, 45);
                }
            });

            resultsContainer.add(scrollPane, BorderLayout.CENTER);
            searchResultsPanel.add(resultsContainer);
        }

        searchResultsPanel.revalidate();
        searchResultsPanel.repaint();
    }

    private void clearSearchResults() {
        searchResultsPanel.removeAll();
        searchResultsPanel.revalidate();
        searchResultsPanel.repaint();
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

            double usdValue = point.getValue();
            String preferredCurrency = getPreferredCurrency();
            CurrencyConverter converter = PreferredCurrencyManager.getConverter();

            double convertedValue;
            if (converter != null) {
                convertedValue = converter.convert(usdValue, "USD", preferredCurrency);
            } else {
                convertedValue = usdValue;
                preferredCurrency = "USD";
            }

            try {
                series.addOrUpdate(day, convertedValue);
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
