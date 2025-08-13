package view.tabbedmain;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import usecase.notifications.NotificationManager;
import view.ui.UiConstants;

public class NotificationDialog {
    private static final int TWO_FIFTY = 250;
    private static final int HEADER_TOP_BOTTOM_PADDING = 15;
    private static final int HEADER_LEFT_RIGHT_PADDING = 20;
    private static final int TWO_HUNDRED = 200;
    private static final String FONT_FAMILY = "Sans Serif";
    private static final int TITLE_FONT_SIZE = 18;
    private static final int COUNT_FONT_SIZE = 14;
    private static final Color COUNT_LABEL_COLOR = new Color(TWO_HUNDRED, TWO_HUNDRED, TWO_HUNDRED);

    private final JFrame parent;
    private final List<String> notifications;
    private final Runnable onNewsTabSwitch;
    private JDialog dialog;

    public NotificationDialog(JFrame parent, List<String> notifications, Runnable onNewsTabSwitch) {
        this.parent = parent;
        this.notifications = notifications;
        this.onNewsTabSwitch = onNewsTabSwitch;
    }

    /**
     * This shows the dialog, it is useless to have this comment
     * the code is obviously made for this purpose and needs no
     * clarification, but Sun Microsystems decided it was important
     * to label everything...
     */
    public void showDialog() {
        final int fiveHundred = 500;
        final int threeHundred = 300;
        dialog = new JDialog(parent, "Notifications", true);
        dialog.setSize(fiveHundred, threeHundred);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        dialog.add(createHeaderPanel(), BorderLayout.NORTH);
        dialog.add(createContentPanel(), BorderLayout.CENTER);
        dialog.add(createButtonPanel(), BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UiConstants.Colors.PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(HEADER_TOP_BOTTOM_PADDING,
                HEADER_LEFT_RIGHT_PADDING, HEADER_TOP_BOTTOM_PADDING, HEADER_LEFT_RIGHT_PADDING));

        JLabel titleLabel = new JLabel("📢 Notifications");
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setForeground(UiConstants.Colors.ON_PRIMARY);

        JLabel countLabel = new JLabel(notifications.size() + " notification(s)");
        countLabel.setFont(new Font(FONT_FAMILY, Font.PLAIN, COUNT_FONT_SIZE));
        countLabel.setForeground(new Color(TWO_HUNDRED, TWO_HUNDRED, TWO_HUNDRED));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(countLabel, BorderLayout.EAST);
        return headerPanel;
    }

    private JScrollPane createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(HEADER_TOP_BOTTOM_PADDING,
            HEADER_TOP_BOTTOM_PADDING, HEADER_TOP_BOTTOM_PADDING, HEADER_TOP_BOTTOM_PADDING));

        for (String notification : notifications) {
            JPanel notifPanel = createNotificationItem(notification);
            contentPanel.add(notifPanel);
            contentPanel.add(Box.createVerticalStrut(HEADER_TOP_BOTTOM_PADDING));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private JPanel createNotificationItem(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(TWO_FIFTY, 250, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(HEADER_TOP_BOTTOM_PADDING,
                    HEADER_TOP_BOTTOM_PADDING, HEADER_TOP_BOTTOM_PADDING, HEADER_TOP_BOTTOM_PADDING)));

        JLabel messageLabel = new JLabel("<html><div style='width:400px'>" + message + "</div></html>");
        messageLabel.setFont(new Font(FONT_FAMILY, Font.PLAIN, 13));
        messageLabel.setForeground(new Color(60, 60, 60));

        JLabel timeLabel = new JLabel("Just now");
        timeLabel.setFont(new Font(FONT_FAMILY, Font.ITALIC, 11));
        timeLabel.setForeground(new Color(150, 150, 150));

        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(timeLabel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        JButton newsButton = createStyledButton("Open News", UiConstants.Colors.PRIMARY,
                e -> {
                    dialog.dispose();
                    onNewsTabSwitch.run();
                });

        JButton clearButton = createStyledButton("Clear All", UiConstants.Colors.DANGER,
                e -> {
                    NotificationManager.getInstance().clearNotifications();
                    dialog.dispose();
                });

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font(FONT_FAMILY, Font.PLAIN, 12));
        closeButton.setForeground(new Color(100, 100, 100));
        closeButton.setBackground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1));
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(newsButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(closeButton);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color backgroundColor, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font(FONT_FAMILY, Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        return button;
    }
}
