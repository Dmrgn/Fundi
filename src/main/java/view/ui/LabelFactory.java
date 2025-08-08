package view.ui;

import java.awt.*;

import javax.swing.*;

public final class LabelFactory {

    private LabelFactory() {

    }

    /**
     * Create a titled label.
     * @param text The text
     * @return The title label
     */
    public static JLabel createTitleLabel(String text) {
        JLabel title = new JLabel(text);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(UiConstants.Fonts.TITLE);
        title.setForeground(UiConstants.Colors.ON_PRIMARY);
        return title;
    }

    /**
     * Create a form label.
     * @param text the text
     * @return The label
     */
    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UiConstants.Fonts.FORM);
        label.setForeground(UiConstants.Colors.ON_PRIMARY);

        return label;
    }

    /**
     * Create a regular label.
     * @param text The text
     * @return The label
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UiConstants.Fonts.LABEL);
        label.setForeground(UiConstants.Colors.ON_PRIMARY);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Create a list item label.
     * @param text The text
     * @return The label
     */
    public static JLabel createListItemLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UiConstants.Fonts.BODY);
        label.setForeground(UiConstants.Colors.ON_PRIMARY);
        return label;
    }

    /**
     * Create a stat label.
     * @return The label
     */
    public static JLabel createStatLabel() {
        JLabel statLabel = new JLabel();
        statLabel.setFont(UiConstants.Fonts.BODY);
        statLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        return statLabel;
    }

    /**
     * Create a stat label.
     * @param text label text
     * @return The label
     */
    public static JLabel createStatLabel(String text) {
        JLabel statLabel = new JLabel(text);
        statLabel.setFont(UiConstants.Fonts.BODY);
        statLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        return statLabel;
    }

    /**
     * Create a stat Title.
     * @param text The text
     * @return The label
     */
    public static JLabel createStatTitleLabel(String text) {
        JLabel statLabel = new JLabel(text);
        statLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statLabel.setFont(UiConstants.Fonts.HEADING);
        statLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        return statLabel;
    }
}

