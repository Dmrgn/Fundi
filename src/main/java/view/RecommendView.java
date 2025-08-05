package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendState;
import interface_adapter.recommend.RecommendViewModel;
import view.components.LabelFactory;
import view.components.PanelFactory;
import view.components.TitledBorderFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * The View for the Recommend Use Case
 */
public class RecommendView extends BaseView {
    private final RecommendViewModel recommendViewModel;
    private final RecommendController recommendController;

    private static final String LABEL = "Recs: ";
    private final JPanel haveRecsPanel = PanelFactory.createStatListPanel(LABEL);
    private final JPanel notHaveRecsPanel = PanelFactory.createStatListPanel(LABEL);
    private final JPanel safeRecsPanel = PanelFactory.createStatListPanel(LABEL);

    private final BackNavigationHelper backNavigationHelper;

    public RecommendView(RecommendViewModel recommendViewModel, RecommendController recommendController,
            ViewManagerModel viewManagerModel) {
        super("recommend");
        this.recommendViewModel = recommendViewModel;
        this.recommendController = recommendController;
        this.backNavigationHelper = new BackNavigationHelper(viewManagerModel);

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(createBackButtonPanel(e -> {
            // Navigate back to the portfolio view explicitly
            backNavigationHelper.goBackToPortfolio();
        }));

        contentPanel.add(Box.createVerticalStrut(10));

        JPanel havePanel = createSection("Recs In Your Portfolio", haveRecsPanel);
        contentPanel.add(havePanel);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel notHavePanel = createSection("Recs Not In Your Portfolio", notHaveRecsPanel);
        contentPanel.add(notHavePanel);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel safePanel = createSection("Safe Recs In Your Portfolio", safeRecsPanel);
        contentPanel.add(safePanel);
        contentPanel.add(Box.createVerticalStrut(10));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        this.add(scrollPane);
        wireListeners();
    }

    private JPanel createSection(String title, JPanel... detailPanels) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(TitledBorderFactory.createLightTitledBorder(title));
        panel.setForeground(Color.WHITE);

        for (JPanel detailPanel : detailPanels) {
            detailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            detailPanel.setMinimumSize(new Dimension(200, 30));
            panel.add(Box.createVerticalStrut(5));
            panel.add(detailPanel);
        }
        panel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        return panel;
    }

    private void updateListPanel(JPanel panel, Map<String, Double> recs) {
        panel.removeAll();
        int i = 1;
        for (Map.Entry<String, Double> entry : recs.entrySet()) {
            JLabel label = LabelFactory
                    .createListItemLabel(i + ". " + entry.getKey() + ": " + String.format("$%.2f", entry.getValue()));
            panel.add(label);
            i++;
        }
        panel.revalidate();
        panel.repaint();
    }

    private void wireListeners() {
        recommendViewModel.addPropertyChangeListener(e -> {
            RecommendState recommendState = recommendViewModel.getState();
            updateListPanel(haveRecsPanel, recommendState.getHaveRecs());
            updateListPanel(notHaveRecsPanel, recommendState.getNotHaveRecs());
            updateListPanel(safeRecsPanel, recommendState.getSafeRecs());
        });

    }
}
