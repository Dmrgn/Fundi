package view;

import view.BaseView;

import javax.swing.*;
import java.awt.*;
import interface_adapter.navigation.NavigationController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsView extends BaseView {
    public SettingsView(ViewManager viewManager, NavigationController navigationController) {
        super("settings");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Back button at the top
        this.add(createBackButtonPanel(e -> navigationController.goBack()));
        this.add(Box.createVerticalStrut(10));

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Default currency dropdown
        JLabel currencyLabel = new JLabel("Default Currency:");
        String[] currencies = { "USD", "EUR", "CAD", "GBP", "JPY" };
        JComboBox<String> currencyDropdown = new JComboBox<>(currencies);

        // Change password (placeholder)
        JLabel passwordLabel = new JLabel("Change Password:");
        JPasswordField passwordField = new JPasswordField(15);
        JButton updatePassword = new JButton("Update");

        this.add(Box.createVerticalStrut(30));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(currencyLabel);
        this.add(currencyDropdown);
        this.add(Box.createVerticalStrut(20));
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(updatePassword);
        this.add(Box.createVerticalStrut(20));
    }
}
