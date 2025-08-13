package view.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import entity.CurrencyConverter;
import entity.PortfolioValuePoint;
import entity.PreferredCurrencyManager;
import view.ui.ButtonFactory;
import view.ui.UiConstants;

import static entity.PreferredCurrencyManager.getPreferredCurrency;

/**
 * Encapsulates the JFreeChart chart and exposes an update method to mutate
 * dataset only.
 */
public class PortfolioChartPanel extends JPanel {
    private final ChartPanel chartPanel;

    public PortfolioChartPanel() {
        super(new BorderLayout());
        setBackground(UiConstants.Colors.CANVAS_BG);
        setOpaque(true);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(UiConstants.Spacing.LG, UiConstants.Spacing.LG, UiConstants.Spacing.LG,
                        UiConstants.Spacing.LG)));
        setPreferredSize(new Dimension(600, 420));

        JLabel chartTitle = new JLabel("Portfolio Value Over Time (Last 30 Days)");
        chartTitle.setFont(UiConstants.Fonts.HEADING);
        chartTitle.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        chartTitle.setHorizontalAlignment(JLabel.CENTER);
        chartTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, UiConstants.Spacing.SM, 0));
        add(chartTitle, BorderLayout.NORTH);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "Date", "Value", dataset, true, true, false);
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

        add(chartPanel, BorderLayout.CENTER);

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsPanel.setOpaque(false);
        JButton saveButton = ButtonFactory.createSecondaryButton("Share Graph");
        saveButton.addActionListener(e -> saveChart());
        controlsPanel.add(saveButton);
        add(controlsPanel, BorderLayout.SOUTH);
    }

    public void updateChart(List<PortfolioValuePoint> valuePoints) {
        if (chartPanel == null)
            return;

        Map<String, TimeSeries> seriesMap = new HashMap<>();
        long minMillis = Long.MAX_VALUE, maxMillis = Long.MIN_VALUE;
        double minVal = Double.POSITIVE_INFINITY, maxVal = Double.NEGATIVE_INFINITY;

        for (PortfolioValuePoint point : valuePoints) {
            String portfolioName = point.getPortfolioName();
            seriesMap.computeIfAbsent(portfolioName, TimeSeries::new);
            TimeSeries series = seriesMap.get(portfolioName);
            java.util.Date date = java.util.Date
                    .from(point.getDate().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
            Day day = new Day(date);
            double usdValue = point.getValue();

            String preferredCurrency = getPreferredCurrency();
            CurrencyConverter converter = PreferredCurrencyManager.getConverter();

            double convertedValue = usdValue;
            if (converter != null) {
                convertedValue = converter.convert(usdValue, "USD", preferredCurrency);
                series.addOrUpdate(day, convertedValue);
            } else {
                preferredCurrency = "USD";
                series.addOrUpdate(day, convertedValue);
            }

            long t = date.getTime();
            minMillis = Math.min(minMillis, t);
            maxMillis = Math.max(maxMillis, t);
            minVal = Math.min(minVal, point.getValue());
            maxVal = Math.max(maxVal, point.getValue());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (TimeSeries s : seriesMap.values())
            dataset.addSeries(s);

        JFreeChart chart = chartPanel.getChart();
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(dataset);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setDefaultShapesVisible(false);
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, new java.awt.BasicStroke(2.6f));
        }
        plot.setRenderer(renderer);

        if (plot.getDomainAxis() instanceof org.jfree.chart.axis.DateAxis dateAxis) {
            if (minMillis < maxMillis) {
                long days = Math.max(1L, (maxMillis - minMillis) / (1000L * 60 * 60 * 24));
                int stepDays = (int) Math.max(1L, days / 10L);
                dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, stepDays));
                dateAxis.setDateFormatOverride(new SimpleDateFormat("MMM d"));
            }
        }
        if (plot.getRangeAxis() instanceof NumberAxis rangeAxis) {
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

    private void saveChart() {
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
    }
}
