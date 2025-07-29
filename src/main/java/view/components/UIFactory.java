package view.components;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class UIFactory {
    private static final String font = "Sans Serif";

    private UIFactory() {

    }

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

    public static JLabel createTitleLabel(String text) {
        JLabel title = new JLabel(text);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(font, Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        return title;
    }

    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(font, Font.BOLD, 14));
        button.setBackground(new Color(30, 60, 120));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(font, Font.PLAIN, 14));
        label.setForeground(Color.WHITE);

        return label;
    }

    public static JTextField createTextField() {
        JTextField textField = new JTextField("", 30);
        textField.setFont(new Font(font, Font.PLAIN, 14));
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.WHITE);
        return textField;
    }

    public static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField("", 30);
        passwordField.setFont(new Font(font, Font.PLAIN, 14));
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(Color.WHITE);
        return passwordField;
    }

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

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(font, Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

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

    public static JPanel createButtonPanel(JButton... buttons) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        for (JButton button : buttons) {
            buttonPanel.add(Box.createHorizontalGlue());
            buttonPanel.add(button);
            buttonPanel.add(Box.createHorizontalGlue());
        }
        return buttonPanel;
    }

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
}
