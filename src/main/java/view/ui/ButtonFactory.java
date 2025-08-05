package view.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class ButtonFactory {
    static final Color BACKGROUND = new Color(30, 60, 120);
    static final Border BUTTON_BORDER = new EmptyBorder(10, 20, 10, 20);
    static final Border PANEL_BORDER = new EmptyBorder(10, 0, 10, 0);
    static final FlowLayout PANEL_FLOW_LAYOUT = new FlowLayout(FlowLayout.CENTER, 5, 0);

    private ButtonFactory() {

    }

    /**
     * Create a styled button.
     * @param text The text
     * @return The styled button
     */
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(LabelFactory.FONT, Font.BOLD, LabelFactory.FORM_SIZE));
        button.setBackground(BACKGROUND);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BUTTON_BORDER);
        return button;
    }

    /**
     * Create a button panel.
     * @param buttons The buttons
     * @return The panel
     */
    public static JPanel createButtonPanel(JButton... buttons) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(PANEL_FLOW_LAYOUT);
        for (JButton button : buttons) {
            buttonPanel.add(Box.createHorizontalGlue());
            buttonPanel.add(button);
            buttonPanel.add(Box.createHorizontalGlue());
        }
        buttonPanel.setBorder(PANEL_BORDER);
        return buttonPanel;
    }
}
