package view.main;

import java.awt.*;

import javax.swing.*;

import view.ui.UiConstants;
import view.ui.icons.BellIcon;

public class MainNotificationButton extends JButton {
    private int notificationCount;
    private final JLabel badge;

    public MainNotificationButton() {
        final int eightTeen = 18;
        final int eleven = 11;
        final int eight = 8;
        final int twelve = 12;
        setLayout(new BorderLayout());
        setBackground(UiConstants.Colors.SURFACE_BG);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(eight, twelve, eight, twelve)));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new BellIcon(eightTeen, UiConstants.Colors.PRIMARY));
        iconLabel.setForeground(UiConstants.Colors.PRIMARY);
        add(iconLabel, BorderLayout.CENTER);
        badge = new JLabel("0");
        badge.setFont(new Font("SansSerif", Font.BOLD, eleven));
        badge.setForeground(UiConstants.Colors.ON_PRIMARY);
        badge.setBackground(UiConstants.Colors.BADGE_BG);
        badge.setOpaque(true);
        badge.setBorder(UiConstants.BADGE_BORDER);
        badge.setVisible(false);
        add(badge, BorderLayout.EAST);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(UiConstants.Colors.SURFACE_BG);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(UiConstants.Colors.SURFACE_BG);
            }
        });
        addActionListener(ide -> {
            JOptionPane.showMessageDialog(this,
                    "Notifications: " + notificationCount + " new notifications",
                    "Notifications",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Send increment Notification.
     */
    public final void incrementNotification() {
        notificationCount++;
        badge.setText(String.valueOf(notificationCount));
        badge.setVisible(true);
    }

    /**
     * Clear all notifications.
     */
    public final void clearNotifications() {
        notificationCount = 0;
        badge.setText("0");
        badge.setVisible(false);
    }
}
