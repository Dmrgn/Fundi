package view;

import data_access.DBUserDataAccessObject;
import data_access.TickerCache;
import interface_adapter.main.MainViewModel;
import interface_adapter.main.MainState;
import view.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import java.awt.BasicStroke;

// New imports for TwelveData clean architecture
import interface_adapter.watchlist.TimeSeriesController;
import interface_adapter.watchlist.TimeSeriesPresenter;
import interface_adapter.watchlist.TimeSeriesViewModel;
import use_case.watchlist.TimeSeriesInteractor;
import data_access.TwelveDataAccess;
import entity.TimeSeriesPoint;
import org.jfree.data.time.Hour;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.ui.RectangleInsets;

public class WatchlistView extends BaseView implements PropertyChangeListener {
    private final DBUserDataAccessObject userDao;
    private final MainViewModel mainViewModel;
    private JPanel watchlistPanel;
    private JTextField tickerField;

    public WatchlistView(MainViewModel mainViewModel, DBUserDataAccessObject userDao) {
        super("watchlist");
        this.userDao = userDao;
        this.mainViewModel = mainViewModel;
        this.mainViewModel.addPropertyChangeListener(this);

        // Use modern layout similar to Login/Signup views
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 60, 120)); // PRIMARY_COLOUR

        // Create main white panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        // Title section
        JLabel titleLabel = new JLabel("Watchlist");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 60, 120));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(10), gbc);

        // Description
        JLabel descriptionLabel = new JLabel("Track your favorite stocks and monitor their performance.");
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        descriptionLabel.setForeground(Color.DARK_GRAY);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy++;
        mainPanel.add(descriptionLabel, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // Add ticker section
        JPanel addTickerSection = createModernAddTickerPanel();
        gbc.gridy++;
        mainPanel.add(addTickerSection, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // Watchlist display section
        JPanel watchlistSection = createModernWatchlistPanel();
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(watchlistSection, gbc);

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

        // Fetch when the view becomes visible (most up-to-date and avoids rate limit on app start)
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                refreshWatchlist();
            }
        });
    }

    private JPanel createModernAddTickerPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input field
        tickerField = new JTextField();
        tickerField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        tickerField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        tickerField.setPreferredSize(new Dimension(200, 36));

        // Placeholder text behavior
        tickerField.setText("Enter ticker symbol (e.g., AAPL)");
        tickerField.setForeground(Color.GRAY);
        tickerField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (tickerField.getText().equals("Enter ticker symbol (e.g., AAPL)")) {
                    tickerField.setText("");
                    tickerField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (tickerField.getText().isEmpty()) {
                    tickerField.setText("Enter ticker symbol (e.g., AAPL)");
                    tickerField.setForeground(Color.GRAY);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel.add(tickerField, gbc);

        // Add button
        JButton addButton = new JButton("Add to Watchlist");
        addButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addButton.setBackground(new Color(30, 60, 120));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTicker();
            }
        });

        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(5, 10, 5, 5);
        panel.add(addButton, gbc);

        return panel;
    }

    private JPanel createModernWatchlistPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(245, 245, 245));
        container.setOpaque(true);

        // Initialize watchlist panel
        watchlistPanel = new JPanel();
        watchlistPanel.setLayout(new BoxLayout(watchlistPanel, BoxLayout.Y_AXIS));
        watchlistPanel.setBackground(new Color(245, 245, 245));
        watchlistPanel.setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(watchlistPanel);
        scrollPane.setBackground(new Color(245, 245, 245));
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        container.add(scrollPane, BorderLayout.CENTER);

        return container;
    }

    private void addTicker() {
        String ticker = tickerField.getText().trim().toUpperCase();
        if (ticker.isEmpty() || ticker.equals("ENTER TICKER SYMBOL (E.G., AAPL)")) {
            JOptionPane.showMessageDialog(this, "Please enter a ticker symbol.", "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Regex validation for ticker symbol
        if (!ticker.matches("^[A-Z]{1,5}$")) {
            JOptionPane.showMessageDialog(this, "Invalid ticker symbol. Please enter a valid symbol (1-5 letters).",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Finnhub API validation using cached ticker list
        boolean isValidTicker = validateTickerWithFinnhubAPI(ticker);
        if (!isValidTicker) {
            JOptionPane.showMessageDialog(this, ticker + " is not a real stock ticker.", "Invalid Ticker",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        MainState mainState = mainViewModel.getState();
        String username = mainState.getUsername();

        try {
            userDao.addToWatchlist(username, ticker);
            tickerField.setText("Enter ticker symbol (e.g., AAPL)");
            tickerField.setForeground(Color.GRAY);
            refreshWatchlist();
            JOptionPane.showMessageDialog(this, ticker + " added to watchlist", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding ticker to watchlist: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Ticker validation using Finnhub cached ticker list
    private boolean validateTickerWithFinnhubAPI(String ticker) {
        return TickerCache.isValidTicker(ticker);
    }

    private void removeTicker(String ticker) {
        MainState mainState = mainViewModel.getState();
        String username = mainState.getUsername();

        try {
            userDao.removeFromWatchlist(username, ticker);
            refreshWatchlist();
            JOptionPane.showMessageDialog(this, ticker + " removed from watchlist successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error removing ticker from watchlist: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshWatchlist() {
        watchlistPanel.removeAll();

        MainState mainState = mainViewModel.getState();
        if (mainState == null || mainState.getUsername() == null || mainState.getUsername().isEmpty()) {
            JLabel noUserLabel = new JLabel("Please log in to view your watchlist.");
            noUserLabel.setFont(new Font("Sans Serif", Font.ITALIC, 14));
            noUserLabel.setForeground(new Color(150, 150, 150));
            noUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            watchlistPanel.add(noUserLabel);
            watchlistPanel.revalidate();
            watchlistPanel.repaint();
            return;
        }

        String username = mainState.getUsername();

        try {
            List<String> watchlist = userDao.getWatchlist(username);

            if (watchlist.isEmpty()) {
                JLabel emptyLabel = new JLabel("Your watchlist is empty. Add some tickers above!");
                emptyLabel.setFont(new Font("Sans Serif", Font.ITALIC, 14));
                emptyLabel.setForeground(new Color(150, 150, 150));
                emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                watchlistPanel.add(emptyLabel);
            } else {
                for (String ticker : watchlist) {
                    JPanel tickerPanel = createTickerPanel(ticker);
                    watchlistPanel.add(tickerPanel);
                    watchlistPanel.add(Box.createVerticalStrut(5));
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error loading watchlist: " + e.getMessage());
            errorLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
            errorLabel.setForeground(Color.RED);
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            watchlistPanel.add(errorLabel);
        }

        watchlistPanel.revalidate();
        watchlistPanel.repaint();
    }

    private JPanel createTickerPanel(String ticker) {
        String apiTicker = ticker == null ? "" : ticker.trim().toUpperCase();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        // Left: Ticker label and range selector
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        JLabel tickerLabel = new JLabel(apiTicker);
        tickerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        tickerLabel.setForeground(Color.DARK_GRAY);
        JComboBox<String> range = new JComboBox<>(new String[]{"1W", "1M", "3M", "6M", "1Y"});
        range.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JLabel changeLabel = new JLabel("");
        changeLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        left.add(tickerLabel);
        left.add(range);
        left.add(changeLabel);

        // Center: chart holder + error label
        JPanel chartHolder = new JPanel(new BorderLayout());
        chartHolder.setOpaque(false);
        chartHolder.setPreferredSize(new Dimension(520, 180));
        chartHolder.setMinimumSize(new Dimension(360, 160));
        chartHolder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        JLabel errorLabel = new JLabel("");
        errorLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        errorLabel.setForeground(new Color(200, 60, 60));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chartHolder.add(errorLabel, BorderLayout.SOUTH);

        // Store labels for updates
        chartHolder.putClientProperty("ts_tickerLabel", tickerLabel);
        chartHolder.putClientProperty("ts_changeLabel", changeLabel);

        // Wire per-ticker MVC for TwelveData
        ensureTickerController(apiTicker, chartHolder, errorLabel);
        // Initial load async (1W)
        loadTickerChart(apiTicker, "1W", chartHolder, errorLabel);

        // Right: Remove
        JButton removeButton = new JButton("Remove");
        removeButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        removeButton.setBackground(new Color(220, 53, 69));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        removeButton.addActionListener(e -> removeTicker(ticker));
        removeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeButton.setBackground(new Color(200, 35, 51));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeButton.setBackground(new Color(220, 53, 69));
            }
        });

        panel.add(left, BorderLayout.WEST);
        panel.add(chartHolder, BorderLayout.CENTER);
        panel.add(removeButton, BorderLayout.EAST);

        // Range change -> async reload
        range.addActionListener(ev -> {
            String sel = (String) range.getSelectedItem();
            loadTickerChart(apiTicker, sel, chartHolder, errorLabel);
        });

        return panel;
    }

    // Build/ensure one controller+vm per chart holder; presenter updates VM; view listens to VM
    private TimeSeriesController ensureTickerController(String ticker, JPanel chartHolder, JLabel errorLabel) {
        Object existing = chartHolder.getClientProperty("ts_controller");
        if (existing instanceof TimeSeriesController) {
            return (TimeSeriesController) existing;
        }
        TimeSeriesViewModel vm = new TimeSeriesViewModel();
        TimeSeriesPresenter presenter = new TimeSeriesPresenter(vm);
        TimeSeriesInteractor interactor = new TimeSeriesInteractor(new TwelveDataAccess(), presenter);
        TimeSeriesController controller = new TimeSeriesController(interactor);

        vm.addPropertyChangeListener(evt -> {
            if (!TimeSeriesViewModel.PROP_UPDATE.equals(evt.getPropertyName())) return;
            String sym = vm.getSymbol();
            List<TimeSeriesPoint> pts = vm.getPoints();
            String err = vm.getError();
            String range = vm.getRange();
            SwingUtilities.invokeLater(() -> {
                chartHolder.removeAll();
                JLabel tLabel = (JLabel) chartHolder.getClientProperty("ts_tickerLabel");
                JLabel cLabel = (JLabel) chartHolder.getClientProperty("ts_changeLabel");
                if (err != null && !err.isBlank()) {
                    if (tLabel != null) tLabel.setForeground(Color.DARK_GRAY);
                    if (cLabel != null) { cLabel.setText(""); cLabel.setForeground(Color.DARK_GRAY); }
                    errorLabel.setText(err);
                    chartHolder.add(buildEmptyChartPanel(), BorderLayout.CENTER);
                    chartHolder.add(errorLabel, BorderLayout.SOUTH);
                } else if (pts == null || pts.isEmpty()) {
                    if (tLabel != null) tLabel.setForeground(Color.DARK_GRAY);
                    if (cLabel != null) { cLabel.setText("No data"); cLabel.setForeground(new Color(120,120,120)); }
                    errorLabel.setText("No data for this range");
                    chartHolder.add(buildEmptyChartPanel(), BorderLayout.CENTER);
                    chartHolder.add(errorLabel, BorderLayout.SOUTH);
                } else {
                    double first = pts.get(0).getClose();
                    double last = pts.get(pts.size() - 1).getClose();
                    double change = last - first;
                    double pct = first != 0.0 ? (change / first) * 100.0 : 0.0;
                    boolean up = last >= first;
                    Color upColor = new Color(34, 139, 34);
                    Color downColor = new Color(200, 60, 60);
                    if (tLabel != null) tLabel.setForeground(up ? upColor : downColor);
                    if (cLabel != null) {
                        cLabel.setText(String.format((up ? "+%.2f%%" : "%.2f%%"), pct));
                        cLabel.setForeground(up ? upColor : downColor);
                        cLabel.setToolTipText(String.format("Change: %s%.2f (%.2f%%)", up ? "+" : "-", Math.abs(change), Math.abs(pct)));
                    }
                    errorLabel.setText("");
                    ChartPanel panel = buildChartPanelFromPoints(sym, pts, range);
                    chartHolder.add(panel, BorderLayout.CENTER);
                    chartHolder.add(errorLabel, BorderLayout.SOUTH);
                }
                chartHolder.revalidate();
                chartHolder.repaint();
            });
        });

        chartHolder.putClientProperty("ts_vm", vm);
        chartHolder.putClientProperty("ts_controller", controller);
        return controller;
    }

    // --- Async chart loading via Controller ---
    private void loadTickerChart(String ticker, String range, JPanel chartHolder, JLabel errorLabel) {
        // Show loading state
        errorLabel.setText("");
        chartHolder.removeAll();
        JLabel loading = new JLabel("Loading...", SwingConstants.CENTER);
        loading.setForeground(new Color(120, 120, 120));
        chartHolder.add(loading, BorderLayout.CENTER);
        chartHolder.add(errorLabel, BorderLayout.SOUTH);
        chartHolder.revalidate();
        chartHolder.repaint();

        TimeSeriesController controller = ensureTickerController(ticker, chartHolder, errorLabel);
        controller.fetch(ticker, range);
    }

    private ChartPanel buildChartPanelFromPoints(String ticker, List<TimeSeriesPoint> points, String range) {
        TimeSeries series = new TimeSeries(ticker);
        if (points != null) {
            boolean hourly = "1W".equalsIgnoreCase(range);
            for (TimeSeriesPoint p : points) {
                java.util.Date d = new java.util.Date(p.getEpochMillis());
                if (hourly) {
                    series.addOrUpdate(new Hour(d), p.getClose());
                } else {
                    series.addOrUpdate(new Day(d), p.getClose());
                }
            }
        }
        return buildChartPanelFromSeries(series);
    }

    private ChartPanel buildChartPanelFromSeries(TimeSeries series) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        if (series != null) dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "", dataset, false, false, false);
        chart.setBackgroundPaint(Color.WHITE);
        chart.setAntiAlias(true);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setDomainGridlinePaint(new Color(230, 230, 230));
        plot.setRangeGridlinePaint(new Color(230, 230, 230));
        plot.setAxisOffset(new RectangleInsets(2, 2, 2, 2));

        // Smooth spline renderer
        XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setPrecision(5);
        renderer.setSeriesStroke(0, new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Color green/red based on trend
        Color lineColor = new Color(30, 120, 180);
        if (series != null && series.getItemCount() >= 2) {
            double first = series.getValue(0).doubleValue();
            double last = series.getValue(series.getItemCount() - 1).doubleValue();
            if (Double.isFinite(first) && Double.isFinite(last)) {
                if (last >= first) lineColor = new Color(34, 139, 34);
                else lineColor = new Color(200, 60, 60);
            }
        }
        renderer.setSeriesPaint(0, lineColor);
        plot.setRenderer(renderer);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(520, 160));
        panel.setBackground(new Color(245, 245, 245));
        panel.setDomainZoomable(false);
        panel.setRangeZoomable(false);
        return panel;
    }

    private ChartPanel buildEmptyChartPanel() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "", dataset, false, false, false);
        chart.setBackgroundPaint(Color.WHITE);
        chart.setAntiAlias(true);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setDomainGridlinePaint(new Color(240, 240, 240));
        plot.setRangeGridlinePaint(new Color(240, 240, 240));
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(480, 160));
        panel.setBackground(new Color(245, 245, 245));
        return panel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Only refresh if already visible, otherwise defer to componentShown
        if (this.isShowing()) {
            refreshWatchlist();
        }
    }
}
