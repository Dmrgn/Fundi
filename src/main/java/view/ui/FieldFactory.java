package view.ui;

import java.awt.*;

import javax.swing.*;

public class FieldFactory {
    private static final int NUM_COLUMNS = 30;

    /**
     * Create a text field.
     * @return The field
     */
    public static JTextField createTextField() {
        JTextField textField = new JTextField("", NUM_COLUMNS);
        textField.setFont(UiConstants.FORM_FONT);
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.WHITE);
        return textField;
    }

    /**
     * Create a password field.
     * @return The field
     */
    public static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField("", NUM_COLUMNS);
        passwordField.setFont(UiConstants.FORM_FONT);
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(Color.WHITE);
        return passwordField;
    }
}
