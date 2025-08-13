package view.leaderboard;

import javax.swing.*;
import java.awt.*;
import view.ui.UiConstants;

public class LeaderboardHeaderPanel extends JPanel {
    public LeaderboardHeaderPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        setOpaque(false);
        JLabel titleLabel = new JLabel("Leaderboard");
        titleLabel.setFont(UiConstants.Fonts.TITLE);
        titleLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        add(titleLabel);
    }
}
