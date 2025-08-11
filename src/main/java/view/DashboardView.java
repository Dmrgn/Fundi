package view;

import entity.CurrencyConverter;
import entity.PreferredCurrencyManager;
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
import org.jfree.chart.ChartUtils;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;

import static entity.PreferredCurrencyManager.*;

public class DashboardView extends BaseView {
    private final MainViewModel mainViewModel;
    private final SearchController searchController;
    private final SearchViewModel searchViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final DashboardController dashboardController;
    @SuppressWarnings("unused")
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

        // Header
        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(UiConstants.Fonts.TITLE);
        titleLabel.setForeground(Color.WHITE);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(titleLabel);
        header.add(headerLeft, BorderLayout.WEST);

        // Main light canvas panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
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

        // Add main panel to a scroll pane for overflow handling into BaseView content
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane, BorderLayout.CENTER);

        // Set up listeners
        setupListeners();
    }

    private JPanel createModernWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(UiConstants.Colors.CANVAS_BG);
        welcomePanel.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel welcomeLabel = new JLabel("Welcome to Fundi!");
        welcomeLabel.setFont(UiConstants.Fonts.HEADING);
        welcomeLabel.setForeground(UiConstants.Colors.PRIMARY);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        welcomePanel.add(welcomeLabel, gbc);

        return welcomePanel;
    }

    private JPanel createModernSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        searchPanel.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search title
        JLabel searchTitle = new JLabel("Search Stocks");
        searchTitle.setFont(UiConstants.Fonts.HEADING);
        searchTitle.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        searchTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        searchPanel.add(searchTitle, gbc);

        gbc.gridy++;
        searchPanel.add(Box.createVerticalStrut(UiConstants.Spacing.SM), gbc);

        // Search field (centralized)
        JTextField searchField = FieldFactory.createSearchField("Search symbols or companies");
        searchField.setPreferredSize(new Dimension(200, 36));
        searchField.setMaximumSize(new Dimension(200, 36));

        // Add placeholder behavior
        searchField.setText("Search symbols or companies");
        searchField.setForeground(UiConstants.Colors.TEXT_MUTED);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search symbols or companies")) {
                    searchField.setText("");
                    searchField.setForeground(UiConstants.Colors.TEXT_PRIMARY);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search symbols or companies");
                    searchField.setForeground(UiConstants.Colors.TEXT_MUTED);
                }
            }
        });

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        searchPanel.add(searchField, gbc);

        // Search button (centralized)
        JButton searchButton = ButtonFactory.createPrimaryButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 36));

        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.LG, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
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
        usernamePanel.setBackground(UiConstants.Colors.CANVAS_BG);
        usernamePanel.setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(UiConstants.Fonts.BODY);
        usernameLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
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
        chartContainer.setBackground(UiConstants.Colors.CANVAS_BG);
        chartContainer.setOpaque(true);
        chartContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(UiConstants.Spacing.LG, UiConstants.Spacing.LG, UiConstants.Spacing.LG,
                        UiConstants.Spacing.LG)));
        // Preferred size hint only
        chartContainer.setPreferredSize(new Dimension(600, 420));

        JLabel chartTitle = new JLabel("Portfolio Value Over Time (Last 30 Days)");
        chartTitle.setFont(UiConstants.Fonts.HEADING);
        chartTitle.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        chartTitle.setHorizontalAlignment(SwingConstants.CENTER);
        chartTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, UiConstants.Spacing.SM, 0));
        chartContainer.add(chartTitle, BorderLayout.NORTH);

        // Create initial empty chart
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "", // No title since we have a separate label
                "Date",
                "Value",
                dataset,
                true,
                true,
                false);

        // Customize chart appearance for light theme
        chart.setBackgroundPaint(Color.WHITE);
        chart.setAntiAlias(true);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(UiConstants.Colors.SURFACE_BG);
        plot.setDomainGridlinePaint(UiConstants.Colors.GRIDLINE_LIGHT);
        plot.setRangeGridlinePaint(UiConstants.Colors.GRIDLINE_LIGHT);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 360));
        chartPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        chartPanel.setMaximumDrawWidth(Integer.MAX_VALUE);
        chartPanel.setMinimumDrawWidth(0);

        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Controls panel with Save button
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsPanel.setOpaque(false);
        JButton saveButton = ButtonFactory.createSecondaryButton("Share Graph");
        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save chart as PNG");
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
            chooser.setSelectedFile(new File("portfolio_chart.png"));
            int userSelection = chooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = chooser.getSelectedFile();
                if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                    fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".png");
                }
                int width = chartPanel.getWidth() > 0 ? chartPanel.getWidth() : 600;
                int height = chartPanel.getHeight() > 0 ? chartPanel.getHeight() : 360;
                try {
                    ChartUtils.saveChartAsPNG(fileToSave, chartPanel.getChart(), width, height);
                    JOptionPane.showMessageDialog(this, "Chart saved to " + fileToSave.getAbsolutePath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to save chart: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        controlsPanel.add(saveButton);
        chartContainer.add(controlsPanel, BorderLayout.SOUTH);

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
            resultsContainer.setBackground(UiConstants.Colors.CANVAS_BG);
            resultsContainer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                    BorderFactory.createEmptyBorder(UiConstants.Spacing.LG, UiConstants.Spacing.LG,
                            UiConstants.Spacing.LG, UiConstants.Spacing.LG)));

            // Header with title and clear button
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(UiConstants.Colors.CANVAS_BG);

            JLabel resultsTitle = new JLabel("Search Results:");
            resultsTitle.setFont(UiConstants.Fonts.HEADING);
            resultsTitle.setForeground(UiConstants.Colors.TEXT_PRIMARY);

            JButton clearButton = ButtonFactory.createSecondaryButton("Clear");
            clearButton.setPreferredSize(new Dimension(80, 28));
            clearButton.addActionListener(e -> clearSearchResults());

            headerPanel.add(resultsTitle, BorderLayout.WEST);
            headerPanel.add(clearButton, BorderLayout.EAST);
            resultsContainer.add(headerPanel, BorderLayout.NORTH);

            // Content panel for results
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(UiConstants.Colors.CANVAS_BG);
            contentPanel.add(Box.createVerticalStrut(UiConstants.Spacing.SM));

            // Filter and add valid results
            List<SearchResult> validResults = results.stream()
                    .filter(result -> result.getSymbol() != null && !result.getSymbol().isEmpty()
                            && result.getName() != null && !result.getName().isEmpty())
                    .limit(10)
                    .toList();

            for (SearchResult result : validResults) {
                JButton resultButton = ButtonFactory
                        .createOutlinedButton(result.getSymbol() + " - " + result.getName());
                resultButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultButton.addActionListener(e -> {
                    companyDetailsController.execute(result.getSymbol(), "dashboard");
                });
                contentPanel.add(resultButton);
                contentPanel.add(Box.createVerticalStrut(5));
            }

            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
            scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
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
        if (chartPanel == null)
            return;

        Map<String, TimeSeries> portfolioSeries = new HashMap<>();
        long minMillis = Long.MAX_VALUE, maxMillis = Long.MIN_VALUE;
        double minVal = Double.POSITIVE_INFINITY, maxVal = Double.NEGATIVE_INFINITY;

        for (PortfolioValuePoint point : valuePoints) {
            String portfolioName = point.getPortfolioName();
            portfolioSeries.computeIfAbsent(portfolioName, TimeSeries::new);
            TimeSeries series = portfolioSeries.get(portfolioName);
            java.util.Date date = java.util.Date
                    .from(point.getDate().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
            Day day = new Day(date);
            double usdValue = point.getValue();

            String preferredCurrency = getPreferredCurrency();
            CurrencyConverter converter = PreferredCurrencyManager.getConverter();

            double convertedValue;
            if (converter != null) {
                convertedValue = converter.convert(usdValue, "USD", preferredCurrency);
                series.addOrUpdate(day, convertedValue);
            } else {
                convertedValue = usdValue;
                preferredCurrency = "USD";
            }

            long t = date.getTime();
            minMillis = Math.min(minMillis, t);
            maxMillis = Math.max(maxMillis, t);
            minVal = Math.min(minVal, point.getValue());
            maxVal = Math.max(maxVal, point.getValue());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (TimeSeries s : portfolioSeries.values())
            dataset.addSeries(s);

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
