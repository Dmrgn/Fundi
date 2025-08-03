package view;

import interface_adapter.navigation.NavigationController;
import view.components.UIFactory;

import javax.swing.*;
import java.awt.*;

public class WatchlistView extends BaseView {
    private final NavigationController navigationController;

    public WatchlistView(NavigationController navigationController) {
        super("watchlist");
        this.navigationController = navigationController;

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(contentPanel, BorderLayout.CENTER);

        // Title
        contentPanel.add(UIFactory.createTitlePanel("Watchlist"));
        contentPanel.add(Box.createVerticalStrut(30));

        JLabel descriptionLabel = new JLabel("Track your favorite stocks and monitor their performance.");
        descriptionLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(200, 200, 200));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(descriptionLabel);
        contentPanel.add(Box.createVerticalGlue());
    }
}
