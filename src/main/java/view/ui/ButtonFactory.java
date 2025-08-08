package view.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class ButtonFactory {
    /**
     * Create a modern primary blue button.
     */
    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UiConstants.BUTTON_FONT);
        button.setBackground(UiConstants.PRIMARY_COLOUR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(UiConstants.BUTTON_BORDER);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Create a link-style button (flat, blue, underlined text).
     */
    public static JButton createLinkButton(String text) {
        // Use HTML to underline text since Font.UNDERLINE is not supported in Java
        JButton button = new JButton("<html><u>" + text + "</u></html>");
        button.setFont(UiConstants.BUTTON_FONT);
        button.setForeground(UiConstants.PRIMARY_COLOUR);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        return button;
    }

    private ButtonFactory() {

    }

    /**
     * Create a styled button.
     * 
     * @param text The text
     * @return The styled button
     */
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UiConstants.BUTTON_FONT);
        button.setBackground(UiConstants.PRIMARY_COLOUR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(UiConstants.BUTTON_BORDER);
        return button;
    }

    /**
     * Create a button panel.
     * 
     * @param buttons The buttons
     * @return The panel
     */
    public static JPanel createButtonPanel(JButton... buttons) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        populateButtonPanel(buttonPanel, buttons);
        return buttonPanel;
    }

    /**
     * Populate a button panel with buttons.
     * 
     * @param panel   The panel
     * @param buttons The buttons
     */
    public static void populateButtonPanel(JPanel panel, JButton... buttons) {
        panel.removeAll();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        panel.setMaximumSize(UiConstants.BUTTON_PANEL_DIM);
        panel.setBorder(UiConstants.PANEL_BORDER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = UiConstants.INSETS;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        int cols = UiConstants.INSET_SCALING;
        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];

            Dimension size = UiConstants.PREFERRED_COMPONENT_DIM;
            button.setPreferredSize(size);
            button.setMaximumSize(size);
            button.setMinimumSize(size);

            gbc.gridx = i / cols;
            gbc.gridy = i % cols;
            panel.add(button, gbc);
        }

        panel.revalidate();
        panel.repaint();
    }
}
