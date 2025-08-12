package view.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public final class ButtonFactory {

    private ButtonFactory() {

    }

    /**
     * Create a modern primary button using centralized theme colors.
     *
     *  @param text the text to display on the button
     *  @return a primary button ready to use
     */

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UiConstants.Fonts.BUTTON);
        button.setBackground(UiConstants.Colors.PRIMARY);
        button.setForeground(UiConstants.Colors.ON_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY.darker(), 1),
                UiConstants.EMPTY_BUTTON_BORDER));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Secondary button (subtle prominence).
     *
     * @param text the text to display on the button
     * @return a Secondary button ready to use
     */
    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UiConstants.Fonts.BUTTON);
        button.setBackground(UiConstants.Colors.SECONDARY);
        button.setForeground(UiConstants.Colors.ON_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.SECONDARY.darker(), 1),
                UiConstants.EMPTY_BUTTON_BORDER));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Destructive/alert action button.
     *
     * @param text the text to display on the button
     * @return an action/alert button ready to use
     */
    public static JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UiConstants.Fonts.BUTTON);
        button.setBackground(UiConstants.Colors.DANGER);
        button.setForeground(UiConstants.Colors.ON_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.DANGER.darker(), 1),
                UiConstants.EMPTY_BUTTON_BORDER));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Outlined variant using muted border and transparent background.
     *
     * @param text the text to display on the button
     * @return Outlined button ready to use
     */
    public static JButton createOutlinedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UiConstants.Fonts.BUTTON);
        button.setBackground(UiConstants.Colors.SURFACE_BG);
        button.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                UiConstants.EMPTY_BUTTON_BORDER));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Create a link-style button (flat, primary-colored, underlined text).
     * @param text the text to display on the button
     * @return link button ready to use
     */
    public static JButton createLinkButton(String text) {
        JButton button = new JButton("<html><u>" + text + "</u></html>");
        button.setFont(UiConstants.Fonts.BUTTON);
        button.setForeground(UiConstants.Colors.PRIMARY);
        button.setBackground(UiConstants.Colors.SURFACE_BG);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        return button;
    }

    /**
     * Backward-compatible alias for primary button.
     * @param text the text to display on the button
     * @return button ready to use
     */
    public static JButton createStyledButton(String text) {
        return createPrimaryButton(text);
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
