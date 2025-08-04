package view;

import interface_adapter.change_password.ChangePwdController;
import interface_adapter.change_password.ChangePwdViewModel;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends JPanel {
    private final String viewName = "settings";

    private final JComboBox<String> currencyDropdown;
    private final JPasswordField passwordField;
    private final JButton updatePasswordBtn;
    private final JButton logoutBtn;

    private ChangePwdController changePwdController;
    private ChangePwdController controller;

    public SettingsView(ChangePwdViewModel changePwdViewModel, ViewManager viewManager, LoginView loginView) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Default Currency Dropdown ---
        JLabel currencyLabel = new JLabel("Default Currency:");
        currencyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] currencies = {"USD", "EUR", "CAD", "GBP", "JPY"};
        currencyDropdown = new JComboBox<>(currencies);
        currencyDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        currencyDropdown.setMaximumSize(new Dimension(200, 30));

        JLabel passwordLabel = new JLabel("Change Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(200, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        updatePasswordBtn = new JButton("Update Password");
        updatePasswordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ➕ Connect button to controller
        updatePasswordBtn.addActionListener(e -> {
            String newPassword = getNewPassword();
            if (changePwdController != null) {
                changePwdController.execute(newPassword);
            }
        });

        // --- Logout Button ---
        logoutBtn = new JButton("Log Out");
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutBtn.addActionListener(e -> {
            loginView.clearFields();
            viewManager.switchTo("log in");
        });

        // Add all to layout
        add(title);
        add(Box.createVerticalStrut(30));
        add(currencyLabel);
        add(currencyDropdown);
        add(Box.createVerticalStrut(20));
        add(passwordLabel);
        add(passwordField);
        add(Box.createVerticalStrut(10));
        add(updatePasswordBtn);
        add(Box.createVerticalStrut(30));
        add(logoutBtn);
    }


    public void setController(ChangePwdController controller) {
        this.controller = controller;

        // Attach logic to the update button
        updatePasswordBtn.addActionListener(e -> {
            String newPwd = getNewPassword();
            controller.execute(newPwd);
        });
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
