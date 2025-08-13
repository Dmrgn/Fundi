package view.tabbedmain;

import usecase.notifications.NotificationManager;
import view.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NotificationDialog {
    private final JFrame parent;
    private final List<String> notifications;
    private final Runnable onNewsTabSwitch;
    private JDialog dialog;

    public NotificationDialog(JFrame parent, List<String> notifications, Runnable onNewsTabSwitch) {
        this.parent = parent;
        this.notifications = notifications;
        this.onNewsTabSwitch = onNewsTabSwitch;
    }

    public void showDialog() {
        dialog = new JDialog(parent, "Notifications", true);
        dialog.setSize(500, 300);
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
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("📢 Notifications");
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 18));
        titleLabel.setForeground(UiConstants.Colors.ON_PRIMARY);

        JLabel countLabel = new JLabel(notifications.size() + " notification(s)");
        countLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        countLabel.setForeground(new Color(200, 200, 200));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(countLabel, BorderLayout.EAST);
        return headerPanel;
    }

    private JScrollPane createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (String notification : notifications) {
            JPanel notifPanel = createNotificationItem(notification);
            contentPanel.add(notifPanel);
            contentPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private JPanel createNotificationItem(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)));

        JLabel messageLabel = new JLabel("<html><div style='width:400px'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(60, 60, 60));

        JLabel timeLabel = new JLabel("Just now");
        timeLabel.setFont(new Font("Sans Serif", Font.ITALIC, 11));
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
        closeButton.setFont(new Font("Sans Serif", Font.PLAIN, 12));
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
        button.setFont(new Font("Sans Serif", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        return button;
    }
}
