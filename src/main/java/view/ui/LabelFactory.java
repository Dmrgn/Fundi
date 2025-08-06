package view.ui;

import java.awt.*;

import javax.swing.*;

public final class LabelFactory {
    static final String FONT = "Sans Serif";
    static final int TITLE_SIZE = 36;
    static final int LABEL_SIZE = 12;
    static final int FORM_SIZE = 14;
    static final int NORMAL_SIZE = 18;
    static final int STAT_SIZE = 16;

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
        title.setFont(new Font(FONT, Font.BOLD, TITLE_SIZE));
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
        label.setFont(new Font(FONT, Font.PLAIN, FORM_SIZE));
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
        label.setFont(new Font(FONT, Font.PLAIN, NORMAL_SIZE));
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
        label.setFont(new Font(FONT, Font.PLAIN, STAT_SIZE));
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Create a stat label.
     * @return The label
     */
    public static JLabel createStatLabel() {
        JLabel statLabel = new JLabel();
        statLabel.setFont(new Font(FONT, Font.BOLD, NORMAL_SIZE));
        statLabel.setForeground(Color.WHITE);
        return statLabel;
    }
}

