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
        title.setFont(UiConstants.TITLE_FONT);
        title.setForeground(Color.WHITE);
        return title;
    }

    /**
     * Create a form label.
     * @param text the text
     * @return The label
     */
    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UiConstants.FORM_FONT);
        label.setForeground(Color.WHITE);

        return label;
    }

    /**
     * Create a regular label.
     * @param text The text
     * @return The label
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UiConstants.LABEL_FONT);
        label.setForeground(Color.WHITE);
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
        label.setFont(UiConstants.NORMAL_FONT);
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Create a stat label.
     * @return The label
     */
    public static JLabel createStatLabel() {
        JLabel statLabel = new JLabel();
        statLabel.setFont(UiConstants.NORMAL_FONT);
        statLabel.setForeground(Color.WHITE);
        return statLabel;
    }

    /**
     * Create a stat label.
     * @param text label text
     * @return The label
     */
    public static JLabel createStatLabel(String text) {
        JLabel statLabel = new JLabel(text);
        statLabel.setFont(UiConstants.NORMAL_FONT);
        statLabel.setForeground(Color.WHITE);
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
        statLabel.setFont(UiConstants.HEADING_FONT);
        statLabel.setForeground(Color.WHITE);
        return statLabel;
    }
}

