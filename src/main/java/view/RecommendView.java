package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendState;
import interface_adapter.recommend.RecommendViewModel;
import view.ui.LabelFactory;
import view.ui.PanelFactory;
import view.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * The View for the Recommend Use Case.
 */
public class RecommendView extends BaseView {
    private static final String LABEL = "Recs: ";
    private final RecommendViewModel recommendViewModel;
    private final RecommendController recommendController;
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

        contentPanel.add(createBackButtonPanel(evt -> {
            // Navigate back to the portfolio view explicitly
            backNavigationHelper.goBackToPortfolio();
        }));
        contentPanel.add(UiConstants.mediumVerticalGap());

        JPanel havePanel = PanelFactory.createSection("Recs In Your Portfolio",
                LabelFactory.createStatLabel(), haveRecsPanel);
        contentPanel.add(havePanel);
        contentPanel.add(UiConstants.mediumVerticalGap());

        JPanel notHavePanel = PanelFactory.createSection("Recs Not In Your Portfolio",
                LabelFactory.createStatLabel(), notHaveRecsPanel);
        contentPanel.add(notHavePanel);
        contentPanel.add(UiConstants.mediumVerticalGap());

        JPanel safePanel = PanelFactory.createSection("Safe Recs In Your Portfolio",
                LabelFactory.createStatLabel(), safeRecsPanel);
        contentPanel.add(safePanel);
        contentPanel.add(UiConstants.mediumVerticalGap());

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(UiConstants.SCROLL_UNIT_INCREMENT);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        this.add(scrollPane, BorderLayout.CENTER);
        wireListeners();
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
        recommendViewModel.addPropertyChangeListener(evt -> {
            RecommendState recommendState = recommendViewModel.getState();
            updateListPanel(haveRecsPanel, recommendState.getHaveRecs());
            updateListPanel(notHaveRecsPanel, recommendState.getNotHaveRecs());
            updateListPanel(safeRecsPanel, recommendState.getSafeRecs());
        });

    }
}
