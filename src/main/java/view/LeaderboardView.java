package view;

import entity.LeaderboardEntry;
import interfaceadapter.leaderboard.LeaderboardController;
import interfaceadapter.leaderboard.LeaderboardState;
import interfaceadapter.leaderboard.LeaderboardViewModel;
import entity.CurrencyConverter;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import view.ui.UiConstants;
import view.leaderboard.LeaderboardHeaderPanel;
import view.leaderboard.LeaderboardTablePanel;
import view.leaderboard.LeaderboardActionsPanel;
import static entity.PreferredCurrencyManager.getConverter;
import static entity.PreferredCurrencyManager.getPreferredCurrency;

public class LeaderboardView extends AbstractBaseView {
    private final LeaderboardViewModel leaderboardViewModel;
    private final LeaderboardTablePanel tablePanel;
    private boolean loadedOnce = false;

    public LeaderboardView(LeaderboardViewModel leaderboardViewModel,
            LeaderboardController leaderboardController) {
        super("leaderboard");
        this.leaderboardViewModel = leaderboardViewModel;
        this.tablePanel = new view.leaderboard.LeaderboardTablePanel();

        getHeader().add(new LeaderboardHeaderPanel(), BorderLayout.WEST);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL));

        mainPanel.add(new LeaderboardActionsPanel(leaderboardController::execute));
        mainPanel.add(tablePanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContent().add(scrollPane, BorderLayout.CENTER);

        leaderboardViewModel.addPropertyChangeListener(evt -> {
            LeaderboardState state = leaderboardViewModel.getState();
            SwingUtilities.invokeLater(() -> updateLeaderboard(state.getLeaderboardEntries()));
        });

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                if (!loadedOnce) {
                    loadedOnce = true;
                    leaderboardController.execute();
                }
            }
        });
    }

    private void updateLeaderboard(List<LeaderboardEntry> entries) {
        CurrencyConverter converter = getConverter();
        String preferredCurrency = getPreferredCurrency();
        java.util.function.Function<Double, String> valueFormatter = value -> {
            double convertedPrice = value;
            if (converter != null && !preferredCurrency.equals("USD")) {
                try {
                    convertedPrice = converter.convert(value, "USD", preferredCurrency);
                } catch (Exception e) {
                    System.err.println("Currency conversion failed in leaderboard: " + e.getMessage());
                }
            }
            return String.format("%.2f %s", convertedPrice, preferredCurrency);
        };
        tablePanel.setEntries(entries, valueFormatter);
    }
}
