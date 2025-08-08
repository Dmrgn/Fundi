package view;

import data_access.ExchangeAPIDataAccessObject;
import interface_adapter.change_password.ChangePwdController;
import interface_adapter.change_password.ChangePwdViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SettingsView extends JPanel {
    private final String viewName = "settings";

    private final JComboBox<String> currencyDropdown;
    private final JPasswordField passwordField;
    private final JButton updatePasswordBtn;
    private final JButton logoutBtn;

    private ChangePwdController controller;

    public SettingsView(ChangePwdViewModel changePwdViewModel, ViewManager viewManager, LoginView loginView) {
        // Use modern layout with GridBagLayout
        this.setBackground(new Color(30, 60, 120));
        this.setLayout(new GridBagLayout());

        // Main light gray panel - made wider
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray instead of white
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60)); // Wider padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST; // Left align all components
        gbc.insets = new Insets(6, 0, 6, 0); // Less space between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title
        JLabel title = new JLabel("Settings");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel.add(title, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(12), gbc); // tighter spacing

        // Currency section
        JLabel currencyLabel = new JLabel("Default Currency:");
        currencyLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        currencyLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(currencyLabel, gbc);

        ExchangeAPIDataAccessObject currencyDAO = new ExchangeAPIDataAccessObject();
        List<String> currencyList = currencyDAO.getSupportedCurrencies();
        String[] currencies = currencyList.toArray(new String[0]);
        currencyDropdown = new JComboBox<>(currencies);
        currencyDropdown.setFont(new Font("SansSerif", Font.PLAIN, 14));
        currencyDropdown.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        // Wrap dropdown in left-aligned row to avoid stretching
        JPanel currencyRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        currencyRow.setOpaque(false);
        currencyRow.add(currencyDropdown);
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        int prevFill = gbc.fill;
        double prevWeightX = gbc.weightx;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(currencyRow, gbc);
        // Restore defaults for following rows
        gbc.fill = prevFill;
        gbc.weightx = prevWeightX;

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(12), gbc); // tighter spacing

        // Password section
        JLabel passwordLabel = new JLabel("Change Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        JPanel passwordRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passwordRow.setOpaque(false);
        passwordRow.add(passwordField);
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        prevFill = gbc.fill;
        prevWeightX = gbc.weightx;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordRow, gbc);
        // Restore defaults for following rows
        gbc.fill = prevFill;
        gbc.weightx = prevWeightX;

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(8), gbc); // tighter spacing

        // Update password button (25% width, left-aligned)
        updatePasswordBtn = new JButton("Update Password");
        updatePasswordBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        updatePasswordBtn.setBackground(new Color(30, 60, 120));
        updatePasswordBtn.setForeground(Color.WHITE);
        updatePasswordBtn.setFocusPainted(false);
        updatePasswordBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        updatePasswordBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPanel updateRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        updateRow.setOpaque(false);
        updateRow.add(updatePasswordBtn);
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        prevFill = gbc.fill;
        prevWeightX = gbc.weightx;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(updateRow, gbc);
        gbc.fill = prevFill;
        gbc.weightx = prevWeightX;

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(12), gbc); // tighter spacing

        // Logout button (25% width, left-aligned)
        logoutBtn = new JButton("Log Out");
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(220, 50, 50));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPanel logoutRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoutRow.setOpaque(false);
        logoutRow.add(logoutBtn);
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        prevFill = gbc.fill;
        prevWeightX = gbc.weightx;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(logoutRow, gbc);
        gbc.fill = prevFill;
        gbc.weightx = prevWeightX;

        // Action listeners
        updatePasswordBtn.addActionListener(e -> {
            String newPwd = getNewPassword();
            if (controller != null) {
                controller.execute(newPwd);
            }
        });

        logoutBtn.addActionListener(e -> {
            loginView.clearFields();
            viewManager.switchTo("log in");
        });

        // Responsive: keep buttons at 25% of available width
        mainPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int availableW = Math.max(0, mainPanel.getWidth() - 120); // account for panel padding
                int btnW = Math.max(140, (int) (availableW * 0.25));
                Dimension btnSize = new Dimension(btnW, 36);
                updatePasswordBtn.setPreferredSize(btnSize);
                updatePasswordBtn.setMaximumSize(btnSize);
                logoutBtn.setPreferredSize(btnSize);
                logoutBtn.setMaximumSize(btnSize);

                // Inputs must be EXACTLY 2x the update button width
                int fieldW = btnW * 2;
                Dimension fieldSize = new Dimension(fieldW, 36);
                currencyDropdown.setPreferredSize(fieldSize);
                currencyDropdown.setMaximumSize(fieldSize);
                passwordField.setPreferredSize(fieldSize);
                passwordField.setMaximumSize(fieldSize);

                mainPanel.revalidate();
            }
        });

        // Add main panel to a scroll pane for overflow handling
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(new Color(30, 60, 120)); // UiConstants.PRIMARY_COLOUR
        scrollPane.getViewport().setBackground(new Color(30, 60, 120));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Add scroll pane to view with proper resizing
        GridBagConstraints viewGbc = new GridBagConstraints();
        viewGbc.fill = GridBagConstraints.BOTH;
        viewGbc.weightx = 1.0;
        viewGbc.weighty = 1.0;
        this.add(scrollPane, viewGbc);
    }

    public void setController(ChangePwdController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return viewName;
    }

    public String getSelectedCurrency() {
        return (String) currencyDropdown.getSelectedItem();
    }

    public String getNewPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getUpdatePasswordBtn() {
        return updatePasswordBtn;
    }

    public JButton getLogoutBtn() {
        return logoutBtn;
    }
}