package view;

import entity.CurrencyConverter;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.recommend.RecommendController;
import interfaceadapter.recommend.RecommendState;
import interfaceadapter.recommend.RecommendViewModel;
import view.ui.LabelFactory;
import view.ui.PanelFactory;
import view.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static entity.PreferredCurrencyManager.getConverter;
import static entity.PreferredCurrencyManager.getPreferredCurrency;

/**
 * The View for the Recommend Use Case.
 */
public class RecommendView extends AbstractBaseView {
    private static final String LABEL = "Recs: ";
    private final RecommendViewModel recommendViewModel;
    @SuppressWarnings("unused")
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

        // Header back
        getHeader().add(createBackButtonPanel(evt -> backNavigationHelper.goBackToPortfolio()), BorderLayout.WEST);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

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
        getContent().add(scrollPane, BorderLayout.CENTER);

        wireListeners();
    }

    private void updateListPanel(JPanel panel, Map<String, Double> recs) {
        panel.removeAll();
        int i = 1;
        for (Map.Entry<String, Double> entry : recs.entrySet()) {
            CurrencyConverter converter = getConverter();
            String preferredCurrency = getPreferredCurrency();
            double originalValue = entry.getValue();
            double convertedPrice = originalValue;

            if (converter != null && !preferredCurrency.equals("USD")) {
                try {
                    convertedPrice = converter.convert(originalValue, "USD", preferredCurrency);
                }
                catch (Exception ex) {
                    System.err.println("Currency conversion failed: " + ex.getMessage());
                }
            }
            JLabel label = LabelFactory
                    .createListItemLabel(i + ". " + entry.getKey() + ": "
                            + String.format("%.2f %s", convertedPrice, preferredCurrency));
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
