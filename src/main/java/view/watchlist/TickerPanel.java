package view.watchlist;

import interfaceadapter.watchlist.WatchlistController;
import interfaceadapter.watchlist.TimeSeriesController;
import interfaceadapter.watchlist.TimeSeriesPresenter;
import interfaceadapter.watchlist.TimeSeriesViewModel;
import usecase.watchlist.TimeSeriesInteractor;
import dataaccess.TwelveDataAccess;
import entity.TimeSeriesPoint;
import view.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import entity.CurrencyConverter;
import static entity.PreferredCurrencyManager.getConverter;
import static entity.PreferredCurrencyManager.getPreferredCurrency;

public class TickerPanel extends JPanel {
    private final String ticker;
    private final WatchlistController watchlistController;
    private final String username;
    private final JPanel chartHolder;
    private final JLabel errorLabel;
    private final JLabel changeLabel;
    private final JLabel tickerLabel;

    public TickerPanel(String ticker, WatchlistController watchlistController, String username) {
        super(new BorderLayout());
        this.ticker = ticker;
        this.watchlistController = watchlistController;
        this.username = username;

        setBackground(UiConstants.Colors.CANVAS_BG);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(UiConstants.Spacing.LG, UiConstants.Spacing.XL,
                        UiConstants.Spacing.LG, UiConstants.Spacing.XL)));

        // Initialize labels before building panels that use them
        this.tickerLabel = new JLabel(ticker);
        this.changeLabel = new JLabel("");

        // Left: Ticker label and range selector
        JPanel leftPanel = createLeftPanel();

        // Center: chart holder + error label
        chartHolder = new JPanel(new BorderLayout());
        chartHolder.setOpaque(false);
        chartHolder.setPreferredSize(UiConstants.Sizes.CHART_PANEL_WIDE);
        chartHolder.setMinimumSize(new Dimension(360, 160));
        chartHolder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        errorLabel.setForeground(UiConstants.Colors.DANGER);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chartHolder.add(errorLabel, BorderLayout.SOUTH);

        // Right: Remove button
        JButton removeButton = createRemoveButton();

        add(leftPanel, BorderLayout.WEST);
        add(chartHolder, BorderLayout.CENTER);
        add(removeButton, BorderLayout.EAST);

        // Initialize chart with default range
        loadTickerChart("1W");
    }

    private JPanel createLeftPanel() {
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        tickerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        tickerLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);

        JComboBox<String> range = new JComboBox<>(new String[] { "1W", "1M", "3M", "6M", "1Y" });
        range.setFont(new Font("SansSerif", Font.PLAIN, 12));
        range.addActionListener(e -> {
            String selectedRange = (String) range.getSelectedItem();
            loadTickerChart(selectedRange);
        });

        changeLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        left.add(tickerLabel);
        left.add(range);
        left.add(changeLabel);

        return left;
    }

    private JButton createRemoveButton() {
        JButton removeButton = new JButton("Remove");
        removeButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        removeButton.setBackground(UiConstants.Colors.DANGER);
        removeButton.setForeground(UiConstants.Colors.ON_PRIMARY);
        removeButton.setFocusPainted(false);
        removeButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        removeButton.addActionListener(e -> watchlistController.removeTicker(username, ticker));

        removeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeButton.setBackground(UiConstants.Colors.DANGER.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeButton.setBackground(UiConstants.Colors.DANGER);
            }
        });

        return removeButton;
    }

    private void loadTickerChart(String range) {
        // Show loading state
        errorLabel.setText("");
        chartHolder.removeAll();
        JLabel loading = new JLabel("Loading...", SwingConstants.CENTER);
        loading.setForeground(UiConstants.Colors.TEXT_MUTED);
        chartHolder.add(loading, BorderLayout.CENTER);
        chartHolder.add(errorLabel, BorderLayout.SOUTH);
        chartHolder.revalidate();
        chartHolder.repaint();

        // Create time series components
        TimeSeriesViewModel vm = new TimeSeriesViewModel();
        TimeSeriesPresenter presenter = new TimeSeriesPresenter(vm);
        TimeSeriesInteractor interactor = new TimeSeriesInteractor(new TwelveDataAccess(), presenter);
        TimeSeriesController controller = new TimeSeriesController(interactor);

        // Listen for updates
        vm.addPropertyChangeListener(evt -> {
            if (TimeSeriesViewModel.PROP_UPDATE.equals(evt.getPropertyName())) {
                updateChart(vm);
            }
        });

        // Fetch data
        controller.fetch(ticker, range);
    }

    private void updateChart(TimeSeriesViewModel vm) {
        SwingUtilities.invokeLater(() -> {
            chartHolder.removeAll();

            String error = vm.getError();
            List<TimeSeriesPoint> points = vm.getPoints();
            String range = vm.getRange();

            if (error != null && !error.isBlank()) {
                tickerLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
                changeLabel.setText("");
                changeLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
                errorLabel.setText(error);
                chartHolder.add(buildEmptyChartPanel(), BorderLayout.CENTER);
            } else if (points == null || points.isEmpty()) {
                tickerLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
                changeLabel.setText("No data");
                changeLabel.setForeground(UiConstants.Colors.TEXT_MUTED);
                errorLabel.setText("No data for this range");
                chartHolder.add(buildEmptyChartPanel(), BorderLayout.CENTER);
            } else {
                updateLabelsFromPoints(points);
                ChartPanel panel = buildChartPanelFromPoints(ticker, points, range);
                chartHolder.add(panel, BorderLayout.CENTER);
            }

            chartHolder.add(errorLabel, BorderLayout.SOUTH);
            chartHolder.revalidate();
            chartHolder.repaint();
        });
    }

    private void updateLabelsFromPoints(List<TimeSeriesPoint> points) {
        double first = points.get(0).getClose();
        double last = points.get(points.size() - 1).getClose();
        double change = last - first;
        double pct = first != 0.0 ? (change / first) * 100.0 : 0.0;
        boolean up = last >= first;

        Color upColor = UiConstants.Colors.SUCCESS;
        Color downColor = UiConstants.Colors.DANGER;

        tickerLabel.setForeground(up ? upColor : downColor);
        changeLabel.setText(String.format((up ? "+%.2f%%" : "%.2f%%"), pct));
        changeLabel.setForeground(up ? upColor : downColor);
        changeLabel.setToolTipText(String.format("Change: %s%.2f (%.2f%%)",
                up ? "+" : "-", Math.abs(change), Math.abs(pct)));
    }

    private ChartPanel buildChartPanelFromPoints(String ticker, List<TimeSeriesPoint> points, String range) {
        TimeSeries series = new TimeSeries(ticker);
        String preferredCurrency = getPreferredCurrency();
        CurrencyConverter converter = getConverter();

        if ("1W".equals(range)) {
            for (TimeSeriesPoint p : points) {
                java.util.Date d = new java.util.Date(p.getEpochMillis());
                double amount = p.getClose();
                if (converter != null && !preferredCurrency.equals("USD")) {
                    try {
                        amount = converter.convert(amount, "USD", preferredCurrency);
                    } catch (Exception e) {
                        // fallback to USD
                    }
                }
                // Always use Minute for 1W, matching API interval
                series.addOrUpdate(new Minute(d), amount);
            }
        } else {
            boolean hourly = "1W".equalsIgnoreCase(range);

            for (TimeSeriesPoint p : points) {
                java.util.Date d = new java.util.Date(p.getEpochMillis());
                double amount = p.getClose();
                double convertedAmt = amount;
                if (converter != null && !preferredCurrency.equals("USD")) {
                    try {
                        convertedAmt = converter.convert(amount, "USD", preferredCurrency);
                    } catch (Exception e) {
                        System.err.println("Currency conversion failed in watchlist: " + e.getMessage());
                    }
                }
                if (hourly) {
                    series.addOrUpdate(new Hour(d), convertedAmt);
                } else {
                    series.addOrUpdate(new Day(d), convertedAmt);
                }
            }
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "", "", "Price (" + preferredCurrency + ")", dataset, false, false, false);
        chart.setBackgroundPaint(Color.WHITE);
        chart.setAntiAlias(true);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(UiConstants.Colors.SURFACE_BG);
        plot.setDomainGridlinePaint(UiConstants.Colors.GRIDLINE_LIGHTER);
        plot.setRangeGridlinePaint(UiConstants.Colors.GRIDLINE_LIGHTER);

        // Ensure y-axis includes min/max values
        if (series != null && series.getItemCount() > 0) {
            double min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < series.getItemCount(); i++) {
                double v = series.getValue(i).doubleValue();
                min = Math.min(min, v);
                max = Math.max(max, v);
            }
            if (Double.isFinite(min) && Double.isFinite(max)) {
                // Add a small margin for aesthetics
                double margin = (max - min) * 0.05;
                plot.getRangeAxis().setRange(min - margin, max + margin);
            }
        }

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(480, 160));
        panel.setBackground(UiConstants.Colors.CANVAS_BG);
        return panel;
    }

    private ChartPanel buildEmptyChartPanel() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "", dataset, false, false, false);
        chart.setBackgroundPaint(Color.WHITE);
        chart.setAntiAlias(true);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(UiConstants.Colors.SURFACE_BG);
        plot.setDomainGridlinePaint(UiConstants.Colors.GRIDLINE_LIGHTER);
        plot.setRangeGridlinePaint(UiConstants.Colors.GRIDLINE_LIGHTER);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(480, 160));
        panel.setBackground(UiConstants.Colors.CANVAS_BG);
        return panel;
    }
}
