package view;

import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolios.PortfoliosController;
import view.components.UIFactory;
import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;

public class MainView extends BaseView {
    private final MainViewModel mainViewModel;
    private final PortfoliosController portfoliosController;
    private final NewsController newsController;
    private final NavigationController navigationController;

    public MainView(MainViewModel mainViewModel, PortfoliosController portfoliosController, NewsController newsController, NavigationController navigationController) {
        super("main");
        this.mainViewModel = mainViewModel;
        this.portfoliosController = portfoliosController;
        this.newsController = newsController;
        this.navigationController = navigationController;

        JPanel contentPanel = createGradientContentPanel();
        this.add(contentPanel, BorderLayout.CENTER);

        JPanel topPanel = createTopPanel();
        contentPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        contentPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        // Welcome
        JPanel welcomePanel = UIFactory.createTitlePanel("Welcome to Fundi!");
        JButton settingsButton = new JButton();
        try {
            ImageIcon gearIcon = new ImageIcon("resources/gear.png");
            Image gearImg = gearIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            settingsButton.setIcon(new ImageIcon(gearImg));
        } catch (Exception e) {
            settingsButton.setText("Settings");
        }
        settingsButton.setToolTipText("Settings");
        settingsButton.setContentAreaFilled(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setPreferredSize(new Dimension(40, 40));

        welcomePanel.add(settingsButton);

        // Search and Username
        JButton searchButton = UIFactory.createStyledButton("Search");
        JTextField searchField = UIFactory.createTextField();
        JPanel searchPanel = UIFactory.createSingleFieldForm(searchField, searchButton);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        mainViewModel.addPropertyChangeListener(evt -> {
                MainState mainState = mainViewModel.getState();
                usernameLabel.setText("Logged in as: " + mainState.getUsername());
        });
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerStack = new JPanel();
        centerStack.setLayout(new BoxLayout(centerStack, BoxLayout.Y_AXIS));
        centerStack.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerStack.setOpaque(false);
        centerStack.add(searchPanel);
        centerStack.add(Box.createVerticalStrut(5));
        centerStack.add(usernameLabel);

        JPanel centerRow = new JPanel();
        centerRow.setLayout(new BoxLayout(centerRow, BoxLayout.X_AXIS));
        centerRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerRow.setOpaque(false);
        centerRow.add(Box.createHorizontalGlue());
        centerRow.add(centerStack);
        centerRow.add(Box.createHorizontalGlue());

        topPanel.add(welcomePanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(centerRow);

        Runnable doSearch = () -> {
            String query = searchField.getText();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Searching for: " + query);
            }
        };
        searchButton.addActionListener(evt -> doSearch.run());
        searchField.addActionListener(evt -> doSearch.run());

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel promptLabel = new JLabel("What would you like to look at?");
        promptLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        promptLabel.setForeground(Color.WHITE);
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setMaximumSize(new Dimension(400, 100));
        buttonPanel.setOpaque(false);

        String[] useCases = {"Portfolios", "News", "Watchlist", "Leaderboard"};
        for (String useCase : useCases) {
            JButton useCaseButton = new JButton(useCase);
            useCaseButton.setFont(new Font("Sans Serif", Font.BOLD, 16));
            useCaseButton.setBackground(new Color(30, 60, 120));
            useCaseButton.setForeground(Color.WHITE);
            useCaseButton.setFocusPainted(false);
            useCaseButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            useCaseButton.addActionListener(evt -> {
                MainState mainState = mainViewModel.getState();
                mainState.setUseCase(useCase);
                mainViewModel.setState(mainState);
                navigationController.navigateTo(mainViewModel.getViewName(), useCase.toLowerCase());
                switch (useCase) {
                    case "Portfolios" -> portfoliosController.execute(mainState.getUsername());
                    case "News" -> newsController.execute(mainState.getUsername());

                }
            });

            buttonPanel.add(useCaseButton);
        }

        centerPanel.add(promptLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel);

        return centerPanel;
    }
}
