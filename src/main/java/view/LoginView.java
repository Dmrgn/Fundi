package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends BaseView implements PropertyChangeListener {

    private final LoginViewModel loginViewModel;
    private final LoginController loginController;

    private final JTextField usernameField = new JTextField(30);
    private final JPasswordField passwordField = new JPasswordField(30);
    private final JButton loginButton = new JButton("Login");
    private final JButton signUpButton = new JButton("Sign Up");

    public LoginView(LoginViewModel loginViewModel, LoginController loginController) {
        super("log in");
        this.loginViewModel = loginViewModel;
        this.loginController = loginController;
        this.loginViewModel.addPropertyChangeListener(this);

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = createTitlePanel();
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(formPanel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        this.add(contentPanel, BorderLayout.CENTER);

        wireListeners();
    }

    private JPanel createTitlePanel() {
        JLabel title = new JLabel("Login Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Sans Serif", Font.BOLD, 36));
        title.setForeground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(title);
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        LabelTextPanel usernameInfo = new LabelTextPanel(new JLabel("Username"), usernameField);
        LabelTextPanel passwordInfo = new LabelTextPanel(new JLabel("Password"), passwordField);

        form.add(usernameInfo);
        form.add(Box.createVerticalStrut(10));
        form.add(passwordInfo);
        return form;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        styleButton(loginButton);
        styleButton(signUpButton);

        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

        return buttonPanel;

    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Sans Serif", Font.BOLD, 16));
        button.setBackground(new Color(30, 60, 120));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void wireListeners() {
        loginButton.addActionListener(e -> {
            LoginState loginState = loginViewModel.getState();
            loginController.execute(loginState.getUsername(), loginState.getPassword());
        });

        signUpButton.addActionListener(e -> loginController.switchToSignupView());

        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                LoginState loginState = loginViewModel.getState();
                loginState.setUsername(usernameField.getText() + e.getKeyChar());
                loginViewModel.setState(loginState);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                LoginState loginState = loginViewModel.getState();
                loginState.setPassword(new String(passwordField.getPassword()) + e.getKeyChar());
                loginViewModel.setState(loginState);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = ((LoginViewModel) evt.getSource()).getState();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }
}
