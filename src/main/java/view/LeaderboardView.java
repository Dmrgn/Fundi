package view;

import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardViewModel;
import view.components.PanelFactory;

import javax.swing.*;
import java.awt.*;

public class LeaderboardView extends BaseView {
    public LeaderboardView(LeaderboardViewModel leaderboardViewModel,
            LeaderboardController leaderboardController) {
        super("leaderboard");

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(contentPanel, BorderLayout.CENTER);

        // Title
        contentPanel.add(PanelFactory.createTitlePanel("Leaderboard"));
        contentPanel.add(Box.createVerticalStrut(30));

        JLabel descriptionLabel = new JLabel("Compare your portfolio performance with other users.");
        descriptionLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(200, 200, 200));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(descriptionLabel);
        contentPanel.add(Box.createVerticalGlue());
    }
}
