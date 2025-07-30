package view;

import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendState;
import interface_adapter.recommend.RecommendViewModel;
import view.components.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class RecommendView extends BaseView {
    private final RecommendViewModel recommendViewModel;
    private final RecommendController recommendController;

    private static final String LABEL = "Recs: ";
    private final JPanel haveRecsPanel = UIFactory.createStatListPanel(LABEL);
    private final JPanel notHaveRecsPanel = UIFactory.createStatListPanel(LABEL);
    private final JPanel safeRecsPanel = UIFactory.createStatListPanel(LABEL);


    private final JButton backButton = UIFactory.createStyledButton("Back");

    public RecommendView(RecommendViewModel recommendViewModel, RecommendController recommendController) {
        super("recommend");
        this.recommendViewModel = recommendViewModel;
        this.recommendController = recommendController;

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(UIFactory.createTitlePanel("Recommendations"));
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



        contentPanel.add(UIFactory.createButtonPanel(backButton));

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
        panel.setBorder(UIFactory.createLightTitledBorder(title));
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
            JLabel label = UIFactory.createListItemLabel(i + ". " + entry.getKey() + ": " + String.format("$%.2f", entry.getValue()));
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

        backButton.addActionListener(e -> recommendController.routeToPortfolio());
    }
}
