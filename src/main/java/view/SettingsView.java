package view;

import interface_adapter.change_password.ChangePwdController;
import interface_adapter.change_password.ChangePwdViewModel;

import javax.swing.*;
import java.awt.*;

import static view.components.UiFactory.*;

public class SettingsView extends JPanel {
    private final String viewName = "settings";

    private final JComboBox<String> currencyDropdown;
    private final JPasswordField passwordField;
    private final JButton updatePasswordBtn;
    private final JButton logoutBtn;

    private ChangePwdController controller;

    public SettingsView(ChangePwdViewModel changePwdViewModel, ViewManager viewManager, LoginView loginView) {
        JPanel contentPanel = loginView.createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        this.setLayout(new BorderLayout());
        this.add(contentPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel currencyLabel = new JLabel("Default Currency:");
        currencyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currencyLabel.setForeground(Color.WHITE);

        String[] currencies = {"USD", "EUR", "CAD", "GBP", "JPY"};
        currencyDropdown = new JComboBox<>(currencies);
        currencyDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        currencyDropdown.setMaximumSize(new Dimension(200, 30));

        JLabel passwordLabel = new JLabel("Change Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setForeground(Color.WHITE);

        passwordField = createPasswordField();
        passwordField.setMaximumSize(new Dimension(200, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        updatePasswordBtn = createStyledButton("Update Password");
        updatePasswordBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutBtn = createStyledButton("Log Out");
        logoutBtn.setForeground(Color.RED);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(currencyLabel);
        contentPanel.add(currencyDropdown);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(passwordLabel);
        contentPanel.add(passwordField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(updatePasswordBtn);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(logoutBtn);
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