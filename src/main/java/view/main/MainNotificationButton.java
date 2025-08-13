package view.main;

import view.ui.UiConstants;
import view.ui.icons.BellIcon;
import javax.swing.*;
import java.awt.*;

public class MainNotificationButton extends JButton {
    private int notificationCount = 0;
    private final JLabel badge;

    public MainNotificationButton() {
        super();
        setLayout(new BorderLayout());
        setBackground(UiConstants.Colors.SURFACE_BG);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new BellIcon(18, UiConstants.Colors.PRIMARY));
        iconLabel.setForeground(UiConstants.Colors.PRIMARY);
        add(iconLabel, BorderLayout.CENTER);
        badge = new JLabel("0");
        badge.setFont(new Font("SansSerif", Font.BOLD, 11));
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
        addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Notifications: " + notificationCount + " new notifications",
                    "Notifications",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public void incrementNotification() {
        notificationCount++;
        badge.setText(String.valueOf(notificationCount));
        badge.setVisible(true);
    }

    public void clearNotifications() {
        notificationCount = 0;
        badge.setText("0");
        badge.setVisible(false);
    }
}
