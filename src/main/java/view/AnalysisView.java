package view;

import interface_adapter.analysis.AnalysisController;
import interface_adapter.analysis.AnalysisState;
import interface_adapter.analysis.AnalysisViewModel;
import view.components.UIFactory;
import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AnalysisView extends BaseView {
    private final AnalysisViewModel analysisViewModel;
    private final AnalysisController analysisController;

    private final JLabel numTickersLabel = UIFactory.createStatLabel();
    private final JLabel volatilityLabel = UIFactory.createStatLabel();
    private final JLabel returnLabel = UIFactory.createStatLabel();

    private final JPanel topHoldingsPanel = UIFactory.createStatListPanel("Highest Holdings:");
    private final JPanel topVolatilityPanel = UIFactory.createStatListPanel("Most Volatile:");
    private final JPanel lowVolatilityPanel = UIFactory.createStatListPanel("Least Volatile:");
    private final JPanel topReturnPanel = UIFactory.createStatListPanel("Top Returns:");
    private final JPanel lowReturnPanel = UIFactory.createStatListPanel("Worst Returns:");

    private final NavigationController navigationController;

    public AnalysisView(AnalysisViewModel analysisViewModel, AnalysisController analysisController, NavigationController navigationController) {
        super("analysis");
        this.analysisViewModel = analysisViewModel;
        this.analysisController = analysisController;
        this.navigationController = navigationController;


        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(createBackButtonPanel(e -> navigationController.goBack()));
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel spreadPanel = createSection("Spread", numTickersLabel, topHoldingsPanel);
        contentPanel.add(spreadPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel volPanel = createSection("Volatility", volatilityLabel, topVolatilityPanel, lowVolatilityPanel);
        contentPanel.add(volPanel);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel returnPanel = createSection("Return", returnLabel, topReturnPanel, lowReturnPanel);
        contentPanel.add(returnPanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        this.add(scrollPane);
        wireListeners();
    }

    private JPanel createSection(String title, JLabel summaryLabel, JPanel... detailPanels) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(UIFactory.createLightTitledBorder(title));
        panel.setForeground(Color.WHITE);

        summaryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(summaryLabel);
        for (JPanel detailPanel : detailPanels) {
            detailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(Box.createVerticalStrut(5));
            panel.add(detailPanel);
        }
        panel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        return panel;
    }

    private void updateListPanel(JPanel panel, Map<String, Double> data) {
        panel.removeAll();
        int i = 1;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            JLabel label = UIFactory.createListItemLabel(i + ". " + entry.getKey() + ": " + UIFactory.format(entry.getValue()));
            panel.add(label);
            i++;
        }
        panel.revalidate();
        panel.repaint();
    }

    private void wireListeners() {
        analysisViewModel.addPropertyChangeListener(e -> {
            AnalysisState analysisState = analysisViewModel.getState();
            numTickersLabel.setText("Number of Tickers: " + analysisState.getNumTickers());
            volatilityLabel.setText("Total Volatility: " + UIFactory.format(analysisState.getVolatility()));
            returnLabel.setText("Total Return: " + UIFactory.format(analysisState.getPastReturn()));

            updateListPanel(topHoldingsPanel, analysisState.getMajorityTickers());
            updateListPanel(topVolatilityPanel, analysisState.getMostVolTickers());
            updateListPanel(lowVolatilityPanel, analysisState.getLeastVolTickers());
            updateListPanel(topReturnPanel, analysisState.getTopReturns());
            updateListPanel(lowReturnPanel, analysisState.getWorstReturns());
        });

    }
}
