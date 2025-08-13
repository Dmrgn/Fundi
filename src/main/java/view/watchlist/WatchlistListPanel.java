package view.watchlist;

import java.awt.*;

import javax.swing.*;

import interfaceadapter.watchlist.WatchlistController;
import interfaceadapter.watchlist.WatchlistState;
import view.ui.UiConstants;

public class WatchlistListPanel extends JPanel {
    private final WatchlistController controller;
    private final JPanel tickersPanel;
    private String currentUsername;

    public WatchlistListPanel(WatchlistController controller) {
        super(new BorderLayout());
        this.controller = controller;

        setBackground(UiConstants.Colors.CANVAS_BG);
        setOpaque(true);

        // Initialize tickers panel
        tickersPanel = new JPanel();
        tickersPanel.setLayout(new BoxLayout(tickersPanel, BoxLayout.Y_AXIS));
        tickersPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        tickersPanel.setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(tickersPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshWatchlist(WatchlistState state) {
        tickersPanel.removeAll();
        this.currentUsername = state.getUsername();

        if (state.getUsername() == null || state.getUsername().isEmpty()) {
            showMessage("Please log in to view your watchlist.", UiConstants.Colors.TEXT_MUTED);
        }
        else if (state.getTickers().isEmpty()) {
            showMessage("Your watchlist is empty. Add some tickers above!", UiConstants.Colors.TEXT_MUTED);
        } 
        else {
            for (String ticker : state.getTickers()) {
                TickerPanel tickerPanel = new TickerPanel(ticker, controller, currentUsername);
                tickersPanel.add(tickerPanel);
                tickersPanel.add(Box.createVerticalStrut(5));
            }
        }

        tickersPanel.revalidate();
        tickersPanel.repaint();
    }

    private void showMessage(String message, Color color) {
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Sans Serif", Font.ITALIC, 14));
        messageLabel.setForeground(color);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tickersPanel.add(messageLabel);
    }

    public void showError(String errorMessage) {
        tickersPanel.removeAll();
        showMessage("Error loading watchlist: " + errorMessage, UiConstants.Colors.DANGER);
        tickersPanel.revalidate();
        tickersPanel.repaint();
    }
}
