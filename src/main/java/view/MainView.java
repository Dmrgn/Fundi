package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.main.MainController;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;

/**
 * The View for the Main Use Case with navigation tabs.
 */
public class MainView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "main";

    private final MainViewModel mainViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private MainController mainController;

    private JButton logoutButton;
    private final JTabbedPane tabbedPane;

    public MainView(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        mainViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Create tabbed navigation
        tabbedPane = createTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        // Create footer panel
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel welcomeLabel = new JLabel("Welcome to Fundi Dashboard");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);

        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        return headerPanel;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();

        // Dashboard Tab
        JPanel dashboardPanel = createDashboardPanel();
        tabs.addTab("Dashboard", dashboardPanel);

        // Profile Tab
        JPanel profilePanel = createProfilePanel();
        tabs.addTab("Profile", profilePanel);

        // Analytics Tab
        JPanel analyticsPanel = createAnalyticsPanel();
        tabs.addTab("Analytics", analyticsPanel);

        // Settings Tab
        JPanel settingsPanel = createSettingsPanel();
        tabs.addTab("Settings", settingsPanel);

        return tabs;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Welcome message
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel welcomeLabel = new JLabel("Dashboard Overview");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(welcomeLabel, gbc);

        // Stats cards
        gbc.gridwidth = 1;
        gbc.gridy = 1;

        JPanel statsPanel = createStatsPanel();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(statsPanel, gbc);

        // Recent activity
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JPanel activityPanel = createActivityPanel();
        panel.add(activityPanel, gbc);

        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Profile header
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel profileLabel = new JLabel("User Profile");
        profileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(profileLabel, gbc);

        // Username field
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameInputField.setPreferredSize(new java.awt.Dimension(200, 25));
        panel.add(usernameInputField, gbc);

        // Email field
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField("user@example.com", 15);
        emailField.setPreferredSize(new java.awt.Dimension(200, 25));
        emailField.setEditable(false);
        panel.add(emailField, gbc);

        // Member since
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Member since:"), gbc);

        gbc.gridx = 1;
        JLabel memberSince = new JLabel("January 2024");
        panel.add(memberSince, gbc);

        return panel;
    }

    private JPanel createAnalyticsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Analytics header
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel analyticsLabel = new JLabel("Analytics & Reports");
        analyticsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(analyticsLabel, gbc);

        // Placeholder content
        gbc.gridy = 1;
        JTextArea analyticsText = new JTextArea(
                "Analytics features coming soon...\n\n" +
                        "• Trend analysis\n" +
                        "• Stock performance metrics\n" +
                        "• News data\n" +
                        "• Other cool stuff");
        analyticsText.setEditable(false);
        analyticsText.setBackground(panel.getBackground());
        analyticsText.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(analyticsText, gbc);

        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Settings header
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(settingsLabel, gbc);

        // Settings options
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Theme:"), gbc);

        gbc.gridx = 1;
        JButton themeButton = new JButton("Change Theme");
        panel.add(themeButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Notifications:"), gbc);

        gbc.gridx = 1;
        JButton notificationsButton = new JButton("Configure");
        panel.add(notificationsButton, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Privacy:"), gbc);

        gbc.gridx = 1;
        JButton privacyButton = new JButton("Privacy Settings");
        panel.add(privacyButton, gbc);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridBagLayout());
        statsPanel.setBorder(BorderFactory.createTitledBorder("Quick Stats"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Total users
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel totalUsersLabel = new JLabel("Market Value:");
        statsPanel.add(totalUsersLabel, gbc);

        gbc.gridx = 1;
        JLabel totalUsersValue = new JLabel("$1,234");
        totalUsersValue.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(totalUsersValue, gbc);

        // Active sessions
        gbc.gridx = 2;
        gbc.gridy = 0;
        JLabel activeSessionsLabel = new JLabel("Percent Gain:");
        statsPanel.add(activeSessionsLabel, gbc);

        gbc.gridx = 3;
        JLabel activeSessionsValue = new JLabel("2.2%");
        activeSessionsValue.setFont(new Font("Arial", Font.BOLD, 14));
        statsPanel.add(activeSessionsValue, gbc);

        return statsPanel;
    }

    private JPanel createActivityPanel() {
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBorder(BorderFactory.createTitledBorder("Recent Activity"));

        JTextArea activityText = new JTextArea(
                "• Trump announced Coca Cola needs to cease operations immediately\n" +
                        "• The world has started to end\n" +
                        "• Mangos have replaced potatoes and now grow in the ground\n" +
                        "• The TSX is down 20% amid rumours Canada is joining US");
        activityText.setEditable(false);
        activityText.setFont(new Font("Monospaced", Font.PLAIN, 12));
        activityText.setBackground(activityPanel.getBackground());

        activityPanel.add(activityText, BorderLayout.CENTER);

        return activityPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        footerPanel.setBackground(new Color(240, 240, 240));

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (mainController != null) {
                            mainController.switchToLoginView();
                        }
                    }
                });

        footerPanel.add(logoutButton, BorderLayout.EAST);

        return footerPanel;
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MainState currentState = mainViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                mainViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Action not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final MainState state = (MainState) evt.getNewValue();
    }

    public String getViewName() {
        return viewName;
    }

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }
}
