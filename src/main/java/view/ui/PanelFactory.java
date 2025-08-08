package view.ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public final class PanelFactory {

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
        panel.setBorder(UiConstants.PANEL_BORDER);
        panel.add(title);
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
        panel.setMaximumSize(UiConstants.PREFERRED_CONTAINER_DIM);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label = LabelFactory.createFormLabel(text);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        label.setForeground(UiConstants.Colors.ON_PRIMARY);
        label.setPreferredSize(UiConstants.PREFERRED_LABEL_DIM);
        label.setMaximumSize(UiConstants.PREFERRED_LABEL_DIM);
        label.setMinimumSize(UiConstants.PREFERRED_LABEL_DIM);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(UiConstants.formGap());
        textField.setPreferredSize(UiConstants.PREFERRED_COMPONENT_DIM);
        textField.setMaximumSize(UiConstants.PREFERRED_COMPONENT_DIM);
        textField.setMinimumSize(UiConstants.PREFERRED_COMPONENT_DIM);
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
        panel.setMaximumSize(UiConstants.SINGLE_DIM);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setPreferredSize(UiConstants.SINGLE_DIM);
        textField.setMaximumSize(UiConstants.SINGLE_DIM);
        textField.setMinimumSize(UiConstants.SINGLE_DIM);

        button.setPreferredSize(UiConstants.FORM_DIM);
        button.setMaximumSize(UiConstants.FORM_DIM);
        button.setMinimumSize(UiConstants.FORM_DIM);

        button.add(Box.createHorizontalGlue());
        panel.add(textField);
        panel.add(UiConstants.formGap());
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
        border.setTitleFont(UiConstants.Fonts.FORM);
        panel.setBorder(border);
        panel.setMinimumSize(UiConstants.SINGLE_DIM);

        panel.setForeground(UiConstants.Colors.ON_PRIMARY);
        return panel;
    }

    /**
     * Create panel with two side by side.
     * @param left Left panel
     * @param right Right panel
     * @return The combined panel
     */
    public static JPanel createSideBySide(JPanel left, JPanel right) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        left.setAlignmentY(Component.TOP_ALIGNMENT);
        right.setAlignmentY(Component.TOP_ALIGNMENT);

        panel.add(left);
        panel.add(UiConstants.bigHorizontalGap());
        panel.add(right);

        return panel;
    }

    /**
     * Create a summary section.
     * @param title Title
     * @param summaryLabel Label
     * @param detailPanels Details
     * @return The summary panel
     */
    public static JPanel createSection(String title, JLabel summaryLabel, JPanel... detailPanels) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UiConstants.TRANSLUCENT_BACKGROUND);
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(
                UiConstants.TRANSLUCENT_BORDER, 1, true),
                UiConstants.EMPTY_BORDER));
        panel.setForeground(UiConstants.Colors.ON_PRIMARY);

        summaryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(LabelFactory.createStatTitleLabel(title));
        panel.add(summaryLabel);
        panel.add(UiConstants.mediumVerticalGap());

        if (detailPanels.length == 1) {
            panel.add(detailPanels[0]);
        } else if (detailPanels.length == 2) {
            panel.add(createSideBySide(detailPanels[0], detailPanels[1]));
        }

        panel.setMaximumSize(UiConstants.PREFERRED_BIG_CONTAINER_DIM);
        return panel;
    }
}
