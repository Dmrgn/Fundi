package view.main;

import java.awt.*;

import javax.swing.*;

import interfaceadapter.main.MainViewModel;
import interfaceadapter.navigation.NavigationController;
import interfaceadapter.news.NewsController;
import interfaceadapter.portfolio_hub.PortfolioHubController;
import view.ui.ButtonFactory;
import view.ui.UiConstants;

public class MainCenterPanel extends JPanel {
    private static final int FIFTY = 50;
    private static final int HUNDRED_FORTY = 140;

    public MainCenterPanel(MainViewModel mainViewModel, NavigationController navigationController,
            PortfolioHubController portfolioHubController, NewsController newsController) {
        super(new GridBagLayout());
        setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.MD, UiConstants.Spacing.MD, UiConstants.Spacing.MD,
                UiConstants.Spacing.MD);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel promptLabel = new JLabel("What would you like to look at?");
        promptLabel.setFont(UiConstants.Fonts.BODY);
        promptLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(promptLabel, gbc);
        gbc.gridy++;
        add(Box.createVerticalStrut(UiConstants.Spacing.XL), gbc);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        int col = 0;
        int row = 2;
        String[] useCases = {"Portfolios", "News", "Watchlist", "Leaderboard"};
        for (String useCase : useCases) {
            JButton useCaseButton = ButtonFactory.createPrimaryButton(useCase);
            useCaseButton.setPreferredSize(new Dimension(HUNDRED_FORTY, FIFTY));
            useCaseButton.addActionListener(evt -> {
                var mainState = mainViewModel.getState();
                mainState.setUseCase(useCase);
                mainViewModel.setState(mainState);
                navigationController.navigateTo(mainViewModel.getViewName(), useCase.toLowerCase());
                switch (useCase) {
                    case "Portfolios" -> portfolioHubController.execute(mainState.getUsername());
                    case "News" -> newsController.execute(mainState.getUsername());
                    default -> {
                        // No action needed for other use cases
                    }
                }
            });
            gbc.gridx = col;
            gbc.gridy = row;
            add(useCaseButton, gbc);
            col++;
            if (col >= 2) {
                col = 0;
                row++;
            }
        }
    }
}
