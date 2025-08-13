package view.tabbedmain;

import usecase.notifications.NotificationManager;
import view.ui.UiConstants;
import view.ui.icons.BellIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NotificationPanel extends JPanel {
    private final JButton notificationButton;
    private final JLabel notificationBadge;
    private int notificationCount = 0;

    public NotificationPanel() {
        setLayout(new OverlayLayout(this));
        setOpaque(false);

        notificationButton = createNotificationButton();
        notificationBadge = createNotificationBadge();

        // Add button first (bottom layer)
        notificationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        notificationButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(notificationButton);

        // Add badge on top, positioned in top-right corner
        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        badgePanel.setOpaque(false);
        badgePanel.add(notificationBadge);
        badgePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        badgePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        add(badgePanel);
    }

    private JButton createNotificationButton() {
        JButton button = new JButton();
        button.setIcon(new BellIcon(18, UiConstants.Colors.ON_PRIMARY));
        button.setText(null);
        button.setForeground(UiConstants.Colors.ON_PRIMARY);
        button.setBackground(UiConstants.Colors.PRIMARY);
        button.setPreferredSize(new Dimension(32, 32));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY.darker(), 2),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (notificationCount > 0) {
                    button.setBackground(UiConstants.Colors.DANGER.darker());
                } else {
                    button.setBackground(UiConstants.Colors.PRIMARY.brighter());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (notificationCount > 0) {
                    button.setBackground(UiConstants.Colors.DANGER);
                } else {
                    button.setBackground(UiConstants.Colors.PRIMARY);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (notificationCount > 0) {
                    button.setBackground(UiConstants.Colors.DANGER.darker());
                } else {
                    button.setBackground(UiConstants.Colors.SECONDARY);
                }
            }
        });

        button.addActionListener(e -> showNotificationDialog());
        return button;
    }

    private JLabel createNotificationBadge() {
        JLabel badge = new JLabel("0");
        badge.setFont(new Font("Sans Serif", Font.BOLD, 9));
        badge.setForeground(UiConstants.Colors.ON_PRIMARY);
        badge.setBackground(UiConstants.Colors.DANGER);
        badge.setOpaque(true);
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setVerticalAlignment(SwingConstants.CENTER);
        badge.setPreferredSize(new Dimension(16, 16));
        badge.setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3));
        badge.setVisible(false);
        return badge;
    }

    private void showNotificationDialog() {
        java.util.List<String> notifications = NotificationManager.getInstance().getNotifications();

        if (notifications.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No new notifications", "Notifications",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        NotificationDialog dialog = new NotificationDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                notifications,
                this::switchToNewsTab);
        dialog.showDialog();
    }

    public void addNotification() {
        notificationCount++;
        updateBadge();
    }

    public void clearNotifications() {
        notificationCount = 0;
        updateBadge();
    }

    private void updateBadge() {
        if (notificationCount > 0) {
            notificationBadge.setText(String.valueOf(notificationCount));
            notificationBadge.setVisible(true);
            notificationButton.setBackground(UiConstants.Colors.DANGER);
            notificationButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UiConstants.Colors.DANGER.darker(), 2),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        } else {
            notificationBadge.setVisible(false);
            notificationButton.setBackground(UiConstants.Colors.PRIMARY);
            notificationButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY.darker(), 2),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        }
        notificationButton.repaint();
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    // Callback interface for tab switching
    public interface TabSwitcher {
        void switchToTab(int tabIndex);
    }

    private TabSwitcher tabSwitcher;

    public void setTabSwitcher(TabSwitcher tabSwitcher) {
        this.tabSwitcher = tabSwitcher;
    }

    private void switchToNewsTab() {
        if (tabSwitcher != null) {
            tabSwitcher.switchToTab(2); // News tab
        }
    }
}
