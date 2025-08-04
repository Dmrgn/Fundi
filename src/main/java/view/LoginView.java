package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import view.components.UiFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Login Use Case
 */
public class LoginView extends BaseView implements PropertyChangeListener {

    private final LoginViewModel loginViewModel;
    private final LoginController loginController;

    private final JTextField usernameField = UiFactory.createTextField();
    private final JPasswordField passwordField = UiFactory.createPasswordField();
    private final JButton loginButton = UiFactory.createStyledButton("Login");
    private final JButton signUpButton = UiFactory.createStyledButton("Sign Up");

    public LoginView(LoginViewModel loginViewModel, LoginController loginController) {
        super("log in");
        this.loginViewModel = loginViewModel;
        this.loginController = loginController;
        this.loginViewModel.addPropertyChangeListener(this);

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = UiFactory.createTitlePanel("Login Screen");
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = UiFactory.createButtonPanel(loginButton, signUpButton);

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

    private JPanel createFormPanel() {
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JPanel usernameInfo = UiFactory.createFormPanel("Username", usernameField);
        JPanel passwordInfo = UiFactory.createFormPanel("Password", passwordField);

        form.add(usernameInfo);
        form.add(Box.createVerticalStrut(10));
        form.add(passwordInfo);
        return form;
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
        LoginState state = (LoginState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }
}
