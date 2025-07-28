package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.portfolios.PortfoliosController;

import javax.swing.*;
import java.awt.*;

public class MainView extends BaseView {
    private final MainViewModel mainViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ViewManager viewManager;
    private PortfoliosController portfoliosController;
    private NewsController newsController;

    public MainView(MainViewModel mainViewModel, ViewManagerModel viewManagerModel, ViewManager viewManager) {
        super("main");
        this.mainViewModel = mainViewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewManager = viewManager;

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
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.X_AXIS));
        welcomePanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome to Fundi!");
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 48));
        welcomeLabel.setForeground(Color.WHITE);

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

        welcomePanel.add(Box.createHorizontalGlue());
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createHorizontalGlue());
        welcomePanel.add(settingsButton);

        // Search and Username
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setOpaque(false);
        searchPanel.setMaximumSize(new Dimension(300, 30));
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField searchField = new JTextField(16);
        searchField.setPreferredSize(new Dimension(180, 30));
        searchField.setMaximumSize(new Dimension(180, 30));
        searchField.setMinimumSize(new Dimension(180, 30));

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(90, 30));
        searchButton.setMaximumSize(new Dimension(90, 30));
        searchButton.setMinimumSize(new Dimension(90, 30));

        searchButton.add(Box.createHorizontalGlue());
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchButton);

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

    public void setPortfoliosController(PortfoliosController portfoliosController) {
        this.portfoliosController = portfoliosController;
    }

    public void setNewsController(NewsController newsController) {
        this.newsController = newsController;
    }
}
