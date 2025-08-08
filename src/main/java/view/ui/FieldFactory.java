package view.ui;

import java.awt.*;

import javax.swing.*;

public class FieldFactory {
    private static final int NUM_COLUMNS = UiConstants.NUM_COLUMNS;

    /**
     * Base text field with centralized styles.
     */
    public static JTextField createTextField() {
        JTextField textField = new JTextField("", NUM_COLUMNS);
        textField.setFont(UiConstants.Fonts.FORM);
        textField.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                UiConstants.EMPTY_BUTTON_BORDER));
        return textField;
    }

    /**
     * Subtle, muted placeholder style for search bars.
     */
    public static JTextField createSearchField(String placeholder) {
        JTextField field = createTextField();
        field.setText(placeholder);
        field.setForeground(UiConstants.Colors.TEXT_MUTED);
        return field;
    }

    /**
     * Password field.
     */
    public static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField("", NUM_COLUMNS);
        passwordField.setFont(UiConstants.Fonts.FORM);
        passwordField.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                UiConstants.EMPTY_BUTTON_BORDER));
        return passwordField;
    }
}
