package view;

import java.awt.*;
import java.util.Map;

import javax.swing.*;

import interface_adapter.analysis.AnalysisViewModel;
import interface_adapter.navigation.NavigationController;
import view.ui.LabelFactory;
import view.ui.PanelFactory;
import view.ui.UiConstants;

public class AnalysisView extends BaseView {
    private static final double TOLERANCE = 0.01;

    private final AnalysisViewModel analysisViewModel;
    private final NavigationController navigationController;

    private final JLabel numTickersLabel = LabelFactory.createStatLabel();
    private final JLabel volatilityLabel = LabelFactory.createStatLabel();
    private final JLabel returnLabel = LabelFactory.createStatLabel();

    private final JPanel topHoldingsPanel = PanelFactory.createStatListPanel("Highest Holdings:");
    private final JPanel topVolatilityPanel = PanelFactory.createStatListPanel("Most Volatile:");
    private final JPanel lowVolatilityPanel = PanelFactory.createStatListPanel("Least Volatile:");
    private final JPanel topReturnPanel = PanelFactory.createStatListPanel("Top Returns:");
    private final JPanel lowReturnPanel = PanelFactory.createStatListPanel("Worst Returns:");

    public AnalysisView(AnalysisViewModel analysisViewModel, NavigationController navigationController) {
        super("analysis");
        this.analysisViewModel = analysisViewModel;

        this.navigationController = navigationController;

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(createBackButtonPanel(evt -> this.navigationController.goBack()), BorderLayout.NORTH);
        contentPanel.add(UiConstants.mediumVerticalGap());

        JPanel spreadPanel = PanelFactory.createSection("Spread", numTickersLabel, topHoldingsPanel);
        contentPanel.add(spreadPanel);
        contentPanel.add(UiConstants.mediumVerticalGap());

        JPanel volPanel = PanelFactory.createSection("Volatility", volatilityLabel, topVolatilityPanel,
                lowVolatilityPanel);
        contentPanel.add(volPanel);
        contentPanel.add(UiConstants.mediumVerticalGap());

        JPanel returnPanel = PanelFactory.createSection("Return", returnLabel, topReturnPanel, lowReturnPanel);
        contentPanel.add(returnPanel);
        contentPanel.add(UiConstants.mediumVerticalGap());
        contentPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(UiConstants.SCROLL_UNIT_INCREMENT);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        this.add(scrollPane, BorderLayout.CENTER);
        wireListeners();
    }

    private void updateListPanel(JPanel panel, Map<String, Double> data) {
        panel.removeAll();
        int i = 1;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            JLabel label = LabelFactory.createListItemLabel(i + ". " + entry.getKey() + ": "
                                                            + format(entry.getValue()));
            panel.add(label);
            i++;
        }
        panel.revalidate();
        panel.repaint();
    }

    private void wireListeners() {
        analysisViewModel.addPropertyChangeListener(evt -> {
            numTickersLabel.setText("Number of Tickers: " + analysisViewModel.getState().getNumTickers());
            volatilityLabel.setText("Total Volatility: " + format(analysisViewModel.getState().getVolatility()));
            returnLabel.setText("Total Return: " + format(analysisViewModel.getState().getPastReturn()));
            updateListPanel(topHoldingsPanel, analysisViewModel.getState().getMajorityTickers());
            updateListPanel(topVolatilityPanel, analysisViewModel.getState().getMostVolTickers());
            updateListPanel(lowVolatilityPanel, analysisViewModel.getState().getLeastVolTickers());
            updateListPanel(topReturnPanel, analysisViewModel.getState().getTopReturns());
            updateListPanel(lowReturnPanel, analysisViewModel.getState().getWorstReturns());
        });
    }

    /**
     * Format a number as a percent.
     * @param value The number
     * @return The percent representation
     */
    private static String format(double value) {
        if (value < TOLERANCE) {
            return "<" + TOLERANCE + "%";
        }

        else {
            return String.format("%.2f%%", value);
        }
    }
}
