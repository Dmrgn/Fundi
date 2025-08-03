package view.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * A class containing helper functions for UI design
 */
public class UIFactory {
    private static final String font = "Sans Serif";

    private UIFactory() {

    }

    /**
     * Create a titled panel
     * @param text The text
     * @return The titled panel
     */
    public static JPanel createTitlePanel(String text) {
        JLabel title = createTitleLabel(text);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(title);
        panel.add(Box.createHorizontalGlue());
        return panel;
    }

    /**
     * Create a titled label
     * @param text The text
     * @return The title label
     */
    public static JLabel createTitleLabel(String text) {
        JLabel title = new JLabel(text);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(font, Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        return title;
    }

    /**
     * Create a styled button
     * @param text The text
     * @return The styled button
     */
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(font, Font.BOLD, 14));
        button.setBackground(new Color(30, 60, 120));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    /**
     * Create a form label
     * @param text the text
     * @return The label
     */
    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(font, Font.PLAIN, 14));
        label.setForeground(Color.WHITE);

        return label;
    }

    /**
     * Create a text field
     * @return The field
     */
    public static JTextField createTextField() {
        JTextField textField = new JTextField("", 30);
        textField.setFont(new Font(font, Font.PLAIN, 14));
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.WHITE);
        return textField;
    }

    /**
     * Create a password field
     * @return The field
     */
    public static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField("", 30);
        passwordField.setFont(new Font(font, Font.PLAIN, 14));
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(Color.WHITE);
        return passwordField;
    }

    /**
     * Create a form panel
     * @param text The text
     * @param textField the text field
     * @return The form panel
     */
    public static JPanel createFormPanel(String text, JTextField textField) {
        JPanel panel = new JPanel();
        JLabel label = createFormLabel(text);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        label.setForeground(Color.WHITE);
        label.setPreferredSize(new Dimension(100, 30));
        label.setMaximumSize(new Dimension(100, 30));
        label.setMinimumSize(new Dimension(100, 30));
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createHorizontalStrut(5));
        textField.setPreferredSize(new Dimension(100, 30));
        textField.setMaximumSize(new Dimension(100, 30));
        textField.setMinimumSize(new Dimension(100, 30));
        textField.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(textField);
        return panel;
    }

    /**
     * Create a regular label
     * @param text The text
     * @return The label
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(font, Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Create a single field form
     * @param textField The text field
     * @param button The button
     * @return The form
     */
    public static JPanel createSingleFieldForm(JTextField textField, JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(300, 30));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setPreferredSize(new Dimension(180, 30));
        textField.setMaximumSize(new Dimension(180, 30));
        textField.setMinimumSize(new Dimension(180, 30));

        button.setPreferredSize(new Dimension(120, 30));
        button.setMaximumSize(new Dimension(120, 30));
        button.setMinimumSize(new Dimension(120, 30));

        button.add(Box.createHorizontalGlue());
        panel.add(textField);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(button);

        return panel;
    }

    /**
     * Create a button panel
     * @param buttons The buttons
     * @return The panel
     */
    public static JPanel createButtonPanel(JButton... buttons) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        for (JButton button : buttons) {
            buttonPanel.add(Box.createHorizontalGlue());
            buttonPanel.add(button);
            buttonPanel.add(Box.createHorizontalGlue());
        }
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return buttonPanel;
    }

    /**
     * Create a styled table
     * @param model The table model
     * @return The table
     */
    public static JScrollPane createStyledTable(TableModel model) {
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setFont(new Font(font, Font.PLAIN, 14));
        table.setGridColor(Color.GRAY);
        table.setShowGrid(true);
        table.setSelectionBackground(Color.WHITE);
        table.setSelectionForeground(Color.BLACK);
        table.setRowMargin(4);
        table.setIntercellSpacing(new Dimension(8, 4));

        table.getTableHeader().setFont(new Font(font, Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setMaximumSize(new Dimension(600, 300));

        return scrollPane;
    }

    /**
     * Format a number as a percent
     * @param value The number
     * @return The percent representation
     */
    public static String format(double value) {
        if (value < 0.01) {
            return String.format("%.1E%%", value);
        } else {
            return String.format("%.2f%%", value);
        }
    }

    /**
     * Create a stat label
     * @return The label
     */
    public static JLabel createStatLabel() {
        JLabel statLabel = new JLabel();
        statLabel.setFont(new Font(font, Font.BOLD, 18));
        statLabel.setForeground(Color.WHITE);
        return statLabel;
    }

    /**
     * Create a list item label
     * @param text The text
     * @return The label
     */
    public static JLabel createListItemLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(font, Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Create a stat list panel
     * @param title The text
     * @return The panel
     */
    public static JPanel createStatListPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        TitledBorder border = createLightTitledBorder(title);
        border.setTitleFont(new Font(font, Font.PLAIN, 12));
        panel.setBorder(border);
        panel.setMinimumSize(new Dimension(200, 30));

        panel.setForeground(Color.WHITE);
        return panel;
    }

    /**
     * Create a titled border
     * @param title The text
     * @return The border
     */
    public static TitledBorder createLightTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), title);
        border.setTitleFont(new Font(font, Font.BOLD, 14));
        border.setTitleColor(Color.WHITE);
        return border;
    }
}
