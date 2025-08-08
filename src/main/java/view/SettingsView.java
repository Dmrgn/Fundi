package view;

import data_access.ExchangeAPIDataAccessObject;
import interface_adapter.change_password.ChangePwdController;
import interface_adapter.change_password.ChangePwdViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import view.ui.UiConstants;

public class SettingsView extends BaseView {
    private final JComboBox<String> currencyDropdown;
    private final JPasswordField passwordField;
    private final JButton updatePasswordBtn;
    private final JButton logoutBtn;

    private ChangePwdController controller;

    public SettingsView(ChangePwdViewModel changePwdViewModel, ViewManager viewManager, LoginView loginView) {
        super("settings");

        // Header
        JLabel title = new JLabel("Settings");
        title.setFont(UiConstants.Fonts.TITLE);
        title.setForeground(UiConstants.Colors.ON_PRIMARY);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(title);
        header.add(headerLeft, BorderLayout.WEST);

        // Main canvas panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XXL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XXL));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(UiConstants.Spacing.SM, 0, UiConstants.Spacing.SM, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Currency section
        JLabel currencyLabel = new JLabel("Default Currency:");
        currencyLabel.setFont(UiConstants.Fonts.BODY);
        currencyLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        gbc.gridy = 0; gbc.gridx = 0; gbc.gridwidth = 1;
        mainPanel.add(currencyLabel, gbc);

        ExchangeAPIDataAccessObject currencyDAO = new ExchangeAPIDataAccessObject();
        List<String> currencyList = currencyDAO.getSupportedCurrencies();
        String[] currencies = currencyList.toArray(new String[0]);
        currencyDropdown = new JComboBox<>(currencies);
        currencyDropdown.setFont(UiConstants.Fonts.FORM);
        currencyDropdown.setBorder(BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1));
        JPanel currencyRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        currencyRow.setOpaque(false);
        currencyRow.add(currencyDropdown);
        gbc.gridy++; gbc.gridwidth = GridBagConstraints.REMAINDER;
        int prevFill = gbc.fill; double prevWeightX = gbc.weightx;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(currencyRow, gbc);
        gbc.fill = prevFill; gbc.weightx = prevWeightX;

        gbc.gridy++; mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.LG), gbc);

        // Password section
        JLabel passwordLabel = new JLabel("Change Password:");
        passwordLabel.setFont(UiConstants.Fonts.BODY);
        passwordLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        gbc.gridy++; gbc.gridwidth = 1;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(UiConstants.Fonts.FORM);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        JPanel passwordRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passwordRow.setOpaque(false);
        passwordRow.add(passwordField);
        gbc.gridy++; gbc.gridwidth = GridBagConstraints.REMAINDER;
        prevFill = gbc.fill; prevWeightX = gbc.weightx;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordRow, gbc);
        gbc.fill = prevFill; gbc.weightx = prevWeightX;

        gbc.gridy++; mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.MD), gbc);

        // Update password button
        updatePasswordBtn = new JButton("Update Password");
        updatePasswordBtn.setFont(UiConstants.Fonts.BUTTON);
        updatePasswordBtn.setBackground(UiConstants.Colors.PRIMARY);
        updatePasswordBtn.setForeground(UiConstants.Colors.ON_PRIMARY);
        updatePasswordBtn.setFocusPainted(false);
        updatePasswordBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        updatePasswordBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPanel updateRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        updateRow.setOpaque(false);
        updateRow.add(updatePasswordBtn);
        gbc.gridy++; gbc.gridwidth = GridBagConstraints.REMAINDER;
        prevFill = gbc.fill; prevWeightX = gbc.weightx;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(updateRow, gbc);
        gbc.fill = prevFill; gbc.weightx = prevWeightX;

        gbc.gridy++; mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.LG), gbc);

        // Logout button
        logoutBtn = new JButton("Log Out");
        logoutBtn.setFont(UiConstants.Fonts.BUTTON);
        logoutBtn.setBackground(UiConstants.Colors.DANGER);
        logoutBtn.setForeground(UiConstants.Colors.ON_PRIMARY);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPanel logoutRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoutRow.setOpaque(false);
        logoutRow.add(logoutBtn);
        gbc.gridy++; gbc.gridwidth = GridBagConstraints.REMAINDER;
        prevFill = gbc.fill; prevWeightX = gbc.weightx;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(logoutRow, gbc);
        gbc.fill = prevFill; gbc.weightx = prevWeightX;

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

        // Responsive resize behavior
        mainPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int availableW = Math.max(0, mainPanel.getWidth() - (UiConstants.Spacing.XXL * 2));
                int btnW = Math.max(140, (int) (availableW * 0.25));
                Dimension btnSize = new Dimension(btnW, 36);
                updatePasswordBtn.setPreferredSize(btnSize);
                updatePasswordBtn.setMaximumSize(btnSize);
                logoutBtn.setPreferredSize(btnSize);
                logoutBtn.setMaximumSize(btnSize);

                int fieldW = btnW * 2;
                Dimension fieldSize = new Dimension(fieldW, 36);
                currencyDropdown.setPreferredSize(fieldSize);
                currencyDropdown.setMaximumSize(fieldSize);
                passwordField.setPreferredSize(fieldSize);
                passwordField.setMaximumSize(fieldSize);

                mainPanel.revalidate();
            }
        });

        // Add to BaseView content via scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane, BorderLayout.CENTER);
    }

    public void setController(ChangePwdController controller) {
        this.controller = controller;
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