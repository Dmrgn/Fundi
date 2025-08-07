package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.PanelFactory;
import view.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Login Use Case.
 */
public class LoginView extends BaseView implements PropertyChangeListener {

    private final LoginViewModel loginViewModel;
    private final LoginController loginController;

    private final JTextField usernameField = FieldFactory.createTextField();
    private final JPasswordField passwordField = FieldFactory.createPasswordField();
    private final JButton loginButton = ButtonFactory.createStyledButton("Login");
    private final JButton signUpButton = ButtonFactory.createStyledButton("Sign Up");

    public LoginView(LoginViewModel loginViewModel, LoginController loginController) {
        super("log in");
        this.loginViewModel = loginViewModel;
        this.loginController = loginController;
        this.loginViewModel.addPropertyChangeListener(this);

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = PanelFactory.createTitlePanel("Login Screen");
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = ButtonFactory.createButtonPanel(loginButton, signUpButton);

        contentPanel.add(UiConstants.bigVerticalGap());
        contentPanel.add(titlePanel);
        contentPanel.add(UiConstants.bigVerticalGap());
        contentPanel.add(formPanel);
        contentPanel.add(UiConstants.mediumVerticalGap());
        contentPanel.add(buttonPanel);
        contentPanel.add(UiConstants.bigVerticalGap());

        this.add(contentPanel, BorderLayout.CENTER);

        wireListeners();
    }

    private JPanel createFormPanel() {
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JPanel usernameInfo = PanelFactory.createFormPanel("Username", usernameField);
        JPanel passwordInfo = PanelFactory.createFormPanel("Password", passwordField);

        form.add(usernameInfo);
        form.add(UiConstants.mediumVerticalGap());
        form.add(passwordInfo);
        return form;
    }

    private void wireListeners() {
        loginButton.addActionListener(evt -> {
            LoginState loginState = loginViewModel.getState();
            loginController.execute(loginState.getUsername(), loginState.getPassword());
        });

        signUpButton.addActionListener(evt -> loginController.switchToSignupView());

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

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");

        LoginState newState = new LoginState();
        loginViewModel.setState(newState);
    }

}
