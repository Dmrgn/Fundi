package view.ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public final class PanelFactory {
    static final int HEIGHT = 30;
    static final int FORM_WIDTH = 100;
    static final int SINGLE_WIDTH = 180;
    static final int SINGLE_PANEL_WIDTH = 300;
    static final Dimension FORM_SIZE = new Dimension(FORM_WIDTH, HEIGHT);
    static final Dimension SINGLE_SIZE = new Dimension(SINGLE_WIDTH, HEIGHT);
    static final Dimension SINGLE_PANEL_SIZE = new Dimension(SINGLE_PANEL_WIDTH, HEIGHT);
    static final Component FORM_GAP = Box.createHorizontalStrut(5);

    private PanelFactory() {

    }

    /**
     * Create a titled panel.
     * @param text The text
     * @return The titled panel
     */
    public static JPanel createTitlePanel(String text) {
        JLabel title = LabelFactory.createTitleLabel(text);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(title);
        panel.add(Box.createHorizontalGlue());
        return panel;
    }

    /**
     * Create a form panel.
     * @param text      The text
     * @param textField the text field
     * @return The form panel
     */
    public static JPanel createFormPanel(String text, JTextField textField) {
        JPanel panel = new JPanel();
        JLabel label = LabelFactory.createFormLabel(text);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        label.setForeground(Color.WHITE);
        label.setPreferredSize(FORM_SIZE);
        label.setMaximumSize(FORM_SIZE);
        label.setMinimumSize(FORM_SIZE);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(FORM_GAP);
        textField.setPreferredSize(FORM_SIZE);
        textField.setMaximumSize(FORM_SIZE);
        textField.setMinimumSize(FORM_SIZE);
        textField.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(textField);
        return panel;
    }

    /**
     * Create a single field form.
     * @param textField The text field
     * @param button    The button
     * @return The form
     */
    public static JPanel createSingleFieldForm(JTextField textField, JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        panel.setMaximumSize(SINGLE_SIZE);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setPreferredSize(SINGLE_SIZE);
        textField.setMaximumSize(SINGLE_SIZE);
        textField.setMinimumSize(SINGLE_SIZE);

        button.setPreferredSize(FORM_SIZE);
        button.setMaximumSize(FORM_SIZE);
        button.setMinimumSize(FORM_SIZE);

        button.add(Box.createHorizontalGlue());
        panel.add(textField);
        panel.add(FORM_GAP);
        panel.add(button);

        return panel;
    }

    /**
     * Create a stat list panel.
     * @param title The text
     * @return The panel
     */
    public static JPanel createStatListPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        TitledBorder border = TitledBorderFactory.createLightTitledBorder(title);
        border.setTitleFont(new Font(LabelFactory.FONT, Font.PLAIN, LabelFactory.LABEL_SIZE));
        panel.setBorder(border);
        panel.setMinimumSize(SINGLE_SIZE);

        panel.setForeground(Color.WHITE);
        return panel;
    }
}
