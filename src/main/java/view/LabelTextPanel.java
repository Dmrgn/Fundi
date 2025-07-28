package view;

import javax.swing.*;
import java.awt.*;

/**
 * A panel containing a label and a text field.
 */
class LabelTextPanel extends JPanel {
    LabelTextPanel(JLabel label, JTextField textField) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);

        label.setForeground(Color.WHITE);
        label.setPreferredSize(new Dimension(100, 30));
        label.setMaximumSize(new Dimension(100, 30));
        label.setMinimumSize(new Dimension(100, 30));
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(label);
        add(Box.createHorizontalStrut(5));
        textField.setPreferredSize(new Dimension(100, 30));
        textField.setMaximumSize(new Dimension(100, 30));
        textField.setMinimumSize(new Dimension(100, 30));
        textField.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(textField);
    }
}