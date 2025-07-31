package view;

import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import view.components.UIFactory;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends BaseView {
    private final MainViewModel mainViewModel;
    private final SearchController searchController;
    private final SearchViewModel searchViewModel;

    public DashboardView(MainViewModel mainViewModel, SearchController searchController,
            SearchViewModel searchViewModel) {
        super("dashboard");
        this.mainViewModel = mainViewModel;
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(contentPanel, BorderLayout.CENTER);

        // Create the dashboard content
        contentPanel.add(createWelcomePanel());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createSearchPanel());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createUsernamePanel());
        contentPanel.add(Box.createVerticalGlue());
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = UIFactory.createTitlePanel("Welcome to Fundi!");

        // Add settings button
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

        return welcomePanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchContainer = new JPanel();
        searchContainer.setLayout(new BoxLayout(searchContainer, BoxLayout.Y_AXIS));
        searchContainer.setOpaque(false);

        // Search title
        JLabel searchTitle = new JLabel("Search Stocks");
        searchTitle.setFont(new Font("Sans Serif", Font.BOLD, 18));
        searchTitle.setForeground(Color.WHITE);
        searchTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Search components
        JButton searchButton = UIFactory.createStyledButton("Search");
        JTextField searchField = UIFactory.createTextField();
        JPanel searchPanel = UIFactory.createSingleFieldForm(searchField, searchButton);

        // Wire up search functionality
        Runnable doSearch = () -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                searchController.execute(query);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search query.");
            }
        };
        searchButton.addActionListener(e -> doSearch.run());
        searchField.addActionListener(e -> doSearch.run());

        searchContainer.add(searchTitle);
        searchContainer.add(Box.createVerticalStrut(10));
        searchContainer.add(searchPanel);

        return searchContainer;
    }

    private JPanel createUsernamePanel() {
        JPanel usernameContainer = new JPanel();
        usernameContainer.setLayout(new BoxLayout(usernameContainer, BoxLayout.Y_AXIS));
        usernameContainer.setOpaque(false);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Listen for username updates
        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                usernameLabel.setText("Logged in as: " + mainState.getUsername());
            }
        });

        usernameContainer.add(usernameLabel);

        return usernameContainer;
    }
}
