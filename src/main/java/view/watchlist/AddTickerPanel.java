package view.watchlist;

import interfaceadapter.watchlist.WatchlistController;
import view.ui.UiConstants;

import javax.swing.*;
import java.awt.*;

public class AddTickerPanel extends JPanel {
    private final WatchlistController controller;
    private final JTextField tickerField;
    private String currentUsername;

    public AddTickerPanel(WatchlistController controller) {
        super(new GridBagLayout());
        this.controller = controller;

        setBackground(UiConstants.Colors.CANVAS_BG);
        setOpaque(true);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(UiConstants.Spacing.LG, UiConstants.Spacing.LG,
                        UiConstants.Spacing.LG, UiConstants.Spacing.LG)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM, UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input field
        tickerField = createTickerField();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        add(tickerField, gbc);

        // Add button
        JButton addButton = createAddButton();
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.LG,
                UiConstants.Spacing.SM, UiConstants.Spacing.SM);
        add(addButton, gbc);
    }

    private JTextField createTickerField() {
        JTextField field = new JTextField();
        field.setFont(UiConstants.Fonts.FORM);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        field.setPreferredSize(new Dimension(200, 36));

        // Placeholder text behavior
        field.setText("Enter ticker symbol (e.g., AAPL)");
        field.setForeground(UiConstants.Colors.TEXT_MUTED);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals("Enter ticker symbol (e.g., AAPL)")) {
                    field.setText("");
                    field.setForeground(UiConstants.Colors.TEXT_PRIMARY);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText("Enter ticker symbol (e.g., AAPL)");
                    field.setForeground(UiConstants.Colors.TEXT_MUTED);
                }
            }
        });

        // Enter key support
        field.addActionListener(e -> addTicker());

        return field;
    }

    private JButton createAddButton() {
        JButton button = new JButton("Add to Watchlist");
        button.setFont(UiConstants.Fonts.BUTTON);
        button.setBackground(UiConstants.Colors.PRIMARY);
        button.setForeground(UiConstants.Colors.ON_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> addTicker());
        return button;
    }

    private void addTicker() {
        String ticker = tickerField.getText().trim();
        if (currentUsername != null && !currentUsername.isEmpty()) {
            controller.addTicker(currentUsername, ticker);
            clearField();
        }
    }

    private void clearField() {
        tickerField.setText("Enter ticker symbol (e.g., AAPL)");
        tickerField.setForeground(UiConstants.Colors.TEXT_MUTED);
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }
}
