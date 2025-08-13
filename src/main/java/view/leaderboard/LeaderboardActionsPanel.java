package view.leaderboard;

import javax.swing.*;
import java.awt.*;
import view.ui.UiConstants;

public class LeaderboardActionsPanel extends JPanel {
    public LeaderboardActionsPanel(Runnable onRefresh) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        JLabel descriptionLabel = new JLabel("Compare your portfolio performance with other users.");
        descriptionLabel.setFont(UiConstants.Fonts.BODY);
        descriptionLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(descriptionLabel);
        add(Box.createVerticalStrut(UiConstants.Spacing.LG));
        JButton refreshButton = new JButton("Refresh Leaderboard");
        refreshButton.setFont(UiConstants.Fonts.BUTTON);
        refreshButton.setBackground(UiConstants.Colors.PRIMARY);
        refreshButton.setForeground(UiConstants.Colors.ON_PRIMARY);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        refreshButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(180, 40));
        refreshButton.addActionListener(e -> onRefresh.run());
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(UiConstants.PRESSED_COLOUR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(UiConstants.Colors.PRIMARY);
            }
        });
        Box buttonRow = Box.createHorizontalBox();
        buttonRow.add(Box.createHorizontalGlue());
        buttonRow.add(refreshButton);
        add(buttonRow);
        add(Box.createVerticalStrut(UiConstants.Spacing.LG));
    }
}
