package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Base form panel for login and signup views.
 */
public class FormPanel extends JPanel {

    public FormPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    protected JTextField createStyledInput() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200)),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return field;
    }

    protected JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(15);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200)),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return field;
    }

    protected JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 119, 71));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    protected static final float CENTER_ALIGNMENT = Component.CENTER_ALIGNMENT;
}
