package view.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public final class BackButtonFactory {

    private BackButtonFactory() {

    }

    /**
     * Call in subclass for a back button.
     *
     * @param backButton The Back Button
     * @param action the ActionListener for the back button
     * @return the panel containing the back button
     */
    public static JPanel createBackButtonPanel(final JButton backButton, java.awt.event.ActionListener action) {
        JPanel panel = new JPanel(UiConstants.BUTTON_LAYOUT);
        panel.setOpaque(false);

        // Create a container for the back button with hover effect
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BorderLayout());
        buttonContainer.setOpaque(false);
        buttonContainer.setBorder(UiConstants.BUTTON_CONTAINER_BORDER);

        // Style the back button
        backButton.setText("← Back");
        backButton.setFont(UiConstants.FORM_FONT);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(UiConstants.PRIMARY_COLOUR);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                UiConstants.LINE_BUTTON_BORDER,
                UiConstants.EMPTY_BUTTON_BORDER
        ));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        backButton.addMouseListener(new HoverAdapter(backButton));

        // Remove any existing listeners and add the new one
        for (java.awt.event.ActionListener l : backButton.getActionListeners()) {
            backButton.removeActionListener(l);
        }
        backButton.addActionListener(action);

        buttonContainer.add(backButton, BorderLayout.CENTER);
        panel.add(buttonContainer);

        return panel;
    }
}
