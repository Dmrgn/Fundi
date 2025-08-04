package view;

import interface_adapter.navigation.NavigationController;
import view.components.UiFactory;

import javax.swing.*;
import java.awt.*;

public class LeaderboardView extends BaseView {
    private final NavigationController navigationController;

    public LeaderboardView(NavigationController navigationController) {
        super("leaderboard");
        this.navigationController = navigationController;

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(contentPanel, BorderLayout.CENTER);

        // Title
        contentPanel.add(UiFactory.createTitlePanel("Leaderboard"));
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
