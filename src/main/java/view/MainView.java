package view;

import interfaceadapter.main.MainState;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.news.NewsController;
import interfaceadapter.portfolio_hub.PortfolioHubController;
import view.ui.ButtonFactory;
import view.ui.UiConstants;
import interfaceadapter.navigation.NavigationController;
import interfaceadapter.search.SearchController;
import interfaceadapter.search.SearchViewModel;
import view.ui.icons.BellIcon;

import javax.swing.*;
import java.awt.*;
import view.ui.FieldFactory;

/**
 * The Main View
 */

import view.main.MainTopPanel;
import view.main.MainCenterPanel;
import view.main.MainNotificationButton;

public class MainView extends AbstractBaseView {
    private final MainNotificationButton notificationButton;

    public MainView(MainViewModel mainViewModel, PortfolioHubController portfolioHubController,
            NewsController newsController, NavigationController navigationController, SearchController searchController,
            SearchViewModel searchViewModel) {
        super("main");
        // Header
        JLabel title = new JLabel("Home");
        title.setFont(UiConstants.Fonts.TITLE);
        title.setForeground(UiConstants.Colors.ON_PRIMARY);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(title);
        getHeader().add(headerLeft, BorderLayout.WEST);
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerRight.setOpaque(false);
        this.notificationButton = new MainNotificationButton();
        headerRight.add(this.notificationButton);
        getHeader().add(headerRight, BorderLayout.EAST);
        // Content
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.anchor = GridBagConstraints.CENTER;
        mainGbc.weightx = 1.0;
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.gridwidth = 1;
        mainPanel.add(new MainTopPanel(mainViewModel, searchController), mainGbc);
        mainGbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.XL), mainGbc);
        mainGbc.gridy++;
        mainPanel.add(new MainCenterPanel(mainViewModel, navigationController, portfolioHubController, newsController),
                mainGbc);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContent().add(scrollPane, BorderLayout.CENTER);
    }

    public void incrementNotification() {
        notificationButton.incrementNotification();
    }

    public void clearNotifications() {
        notificationButton.clearNotifications();
    }
}
