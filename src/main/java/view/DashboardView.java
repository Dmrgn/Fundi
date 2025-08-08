package view;

import entity.PortfolioValuePoint;
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
import view.ui.UiConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

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

        // Use modern layout similar to Login/Signup views
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 60, 120)); // UiConstants.PRIMARY_COLOUR

        // Create main light gray panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray instead of white
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        // Create search results panel (modern styling)
        searchResultsPanel = new JPanel();
        searchResultsPanel.setLayout(new BoxLayout(searchResultsPanel, BoxLayout.Y_AXIS));
        searchResultsPanel.setOpaque(false);

        // Add welcome section
        JPanel welcomeSection = createModernWelcomePanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(welcomeSection, gbc);

        // Add search section
        JPanel searchSection = createModernSearchPanel();
        gbc.gridy++;
        mainPanel.add(searchSection, gbc);

        // Add search results
        gbc.gridy++;
        mainPanel.add(searchResultsPanel, gbc);

        // Add chart section
        JPanel chartSection = createModernPortfolioChart();
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(chartSection, gbc);

        // Add username section
        JPanel usernameSection = createModernUsernamePanel();
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        mainPanel.add(usernameSection, gbc);

        // Add main panel to a scroll pane for overflow handling
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.PRIMARY_COLOUR);
        scrollPane.getViewport().setBackground(UiConstants.PRIMARY_COLOUR);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Add scroll pane to view with proper resizing
        GridBagConstraints viewGbc = new GridBagConstraints();
        viewGbc.fill = GridBagConstraints.BOTH;
        viewGbc.weightx = 1.0;
        viewGbc.weighty = 1.0;
        this.add(scrollPane, viewGbc);

        // Set up listeners
        setupListeners();
    }

    private JPanel createModernWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(new Color(245, 245, 245));
        welcomePanel.setOpaque(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        
        JLabel welcomeLabel = new JLabel("Welcome to Fundi!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(30, 60, 120)); // UiConstants.PRIMARY_COLOUR
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        welcomePanel.add(welcomeLabel, gbc);

        return welcomePanel;
    }

    private JPanel createModernSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.setOpaque(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search title
        JLabel searchTitle = new JLabel("Search Stocks");
        searchTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        searchTitle.setForeground(Color.DARK_GRAY);
        searchTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        searchPanel.add(searchTitle, gbc);

        gbc.gridy++;
        searchPanel.add(Box.createVerticalStrut(10), gbc);

        // Search field
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        searchField.setPreferredSize(new Dimension(200, 36));
        searchField.setMaximumSize(new Dimension(200, 36));
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        searchPanel.add(searchField, gbc);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchButton.setBackground(new Color(30, 60, 120));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(10, 10, 10, 5);
        searchPanel.add(searchButton, gbc);

        // Wire up search functionality
        Runnable doSearch = () -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                searchController.execute(query);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search query.");
            }
        };

        searchButton.addActionListener(e -> {
            doSearch.run();
            searchField.requestFocus();
        });
        searchField.addActionListener(e -> doSearch.run());

        return searchPanel;
    }

    private JPanel createModernUsernamePanel() {
        JPanel usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.setBackground(new Color(245, 245, 245));
        usernamePanel.setOpaque(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.DARK_GRAY);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Listen for username updates
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                usernameLabel.setText("Logged in as: " + mainState.getUsername());
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        usernamePanel.add(usernameLabel, gbc);

        return usernamePanel;
    }

    private JPanel createModernPortfolioChart() {
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(new Color(245, 245, 245));
        chartContainer.setOpaque(true);
        chartContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        // Let layout decide width; remove hard max draw width constraints
        chartContainer.setPreferredSize(new Dimension(600, 420));

        JLabel chartTitle = new JLabel("Portfolio Value Over Time (Last 30 Days)");
        chartTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        chartTitle.setForeground(Color.DARK_GRAY);
        chartTitle.setHorizontalAlignment(SwingConstants.CENTER);
        chartTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        chartContainer.add(chartTitle, BorderLayout.NORTH);

        // Create initial empty chart
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "",  // No title since we have a separate label
                "Date",
                "Value ($)",
                dataset,
                true,
                true,
                false);

        // Customize chart appearance for light theme
        chart.setBackgroundPaint(Color.WHITE);
        chart.setAntiAlias(true);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setDomainGridlinePaint(new Color(230, 230, 230));
        plot.setRangeGridlinePaint(new Color(230, 230, 230));

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 360));
        chartPanel.setBackground(new Color(245, 245, 245));
        // Remove prior hard constraints
        chartPanel.setMaximumDrawWidth(Integer.MAX_VALUE);
        chartPanel.setMinimumDrawWidth(0);

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
            // Create modern styled container for results
            JPanel resultsContainer = new JPanel(new BorderLayout());
            resultsContainer.setBackground(new Color(245, 245, 245));
            resultsContainer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));

            // Header with title and clear button
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(new Color(245, 245, 245));

            JLabel resultsTitle = new JLabel("Search Results:");
            resultsTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
            resultsTitle.setForeground(Color.DARK_GRAY);

            JButton clearButton = new JButton("Clear");
            clearButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
            clearButton.setForeground(Color.WHITE);
            clearButton.setBackground(new Color(180, 50, 50));
            clearButton.setPreferredSize(new Dimension(60, 25));
            clearButton.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            clearButton.setFocusPainted(false);
            clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            clearButton.addActionListener(e -> clearSearchResults());

            headerPanel.add(resultsTitle, BorderLayout.WEST);
            headerPanel.add(clearButton, BorderLayout.EAST);
            resultsContainer.add(headerPanel, BorderLayout.NORTH);

            // Content panel for results
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(new Color(245, 245, 245));
            contentPanel.add(Box.createVerticalStrut(10));

            // Filter and add valid results
            List<SearchResult> validResults = results.stream()
                    .filter(result -> result.getSymbol() != null && !result.getSymbol().isEmpty()
                            && result.getName() != null && !result.getName().isEmpty())
                    .limit(10)
                    .toList();

            for (SearchResult result : validResults) {
                JButton resultButton = new JButton(result.getSymbol() + " - " + result.getName());
                resultButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
                resultButton.setBackground(new Color(30, 60, 120));
                resultButton.setForeground(Color.WHITE);
                resultButton.setFocusPainted(false);
                resultButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                resultButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                resultButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                // Add hover effect
                resultButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        resultButton.setBackground(new Color(40, 70, 130));
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        resultButton.setBackground(new Color(30, 60, 120));
                    }
                });
                
                resultButton.addActionListener(e -> {
                    companyDetailsController.execute(result.getSymbol(), "dashboard");
                });
                contentPanel.add(resultButton);
                contentPanel.add(Box.createVerticalStrut(5));
            }

            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setBackground(new Color(245, 245, 245));
            scrollPane.getViewport().setBackground(new Color(245, 245, 245));
            scrollPane.setBorder(null);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(480, Math.min(300, validResults.size() * 35 + 20)));

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
        if (chartPanel == null) return;

        Map<String, TimeSeries> portfolioSeries = new HashMap<>();
        long minMillis = Long.MAX_VALUE, maxMillis = Long.MIN_VALUE;
        double minVal = Double.POSITIVE_INFINITY, maxVal = Double.NEGATIVE_INFINITY;

        for (PortfolioValuePoint point : valuePoints) {
            String portfolioName = point.getPortfolioName();
            portfolioSeries.computeIfAbsent(portfolioName, TimeSeries::new);
            TimeSeries series = portfolioSeries.get(portfolioName);
            java.util.Date date = java.util.Date.from(point.getDate().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
            Day day = new Day(date);
            try {
                series.addOrUpdate(day, point.getValue());
            } catch (Exception ex) {
                series.update(day, point.getValue());
            }
            long t = date.getTime();
            minMillis = Math.min(minMillis, t);
            maxMillis = Math.max(maxMillis, t);
            minVal = Math.min(minVal, point.getValue());
            maxVal = Math.max(maxVal, point.getValue());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (TimeSeries s : portfolioSeries.values()) dataset.addSeries(s);

        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(dataset);

        // Modern renderer: thicker lines, no shapes
        org.jfree.chart.renderer.xy.XYLineAndShapeRenderer renderer = new org.jfree.chart.renderer.xy.XYLineAndShapeRenderer();
        renderer.setDefaultShapesVisible(false);
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, new java.awt.BasicStroke(2.6f));
        }
        plot.setRenderer(renderer);

        // Axes ticks: balanced, readable
        if (plot.getDomainAxis() instanceof org.jfree.chart.axis.DateAxis dateAxis) {
            if (minMillis < maxMillis) {
                long days = Math.max(1L, (maxMillis - minMillis) / (1000L * 60 * 60 * 24));
                int stepDays = (int) Math.max(1L, days / 10L);
                dateAxis.setTickUnit(new org.jfree.chart.axis.DateTickUnit(
                        org.jfree.chart.axis.DateTickUnitType.DAY, stepDays));
                dateAxis.setDateFormatOverride(new java.text.SimpleDateFormat("MMM d"));
            }
        }
        if (plot.getRangeAxis() instanceof org.jfree.chart.axis.NumberAxis rangeAxis) {
            if (Double.isFinite(minVal) && Double.isFinite(maxVal) && maxVal > minVal) {
                double raw = (maxVal - minVal) / 6.0;
                double magnitude = Math.pow(10, Math.floor(Math.log10(raw)));
                double residual = raw / magnitude;
                double niceFactor = (residual < 1.5) ? 1 : (residual < 3 ? 2 : (residual < 7 ? 5 : 10));
                double unit = niceFactor * magnitude;
                rangeAxis.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(unit));
                rangeAxis.setAutoRangeIncludesZero(false);
            }
        }

        chartPanel.revalidate();
        chartPanel.repaint();
    }
}
