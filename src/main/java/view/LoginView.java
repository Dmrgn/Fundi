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
    private final JButton loginButton = ButtonFactory.createPrimaryButton("Sign in");
    private final JButton signUpButton = ButtonFactory.createLinkButton("create an account");
    private final JCheckBox rememberMeBox = new JCheckBox("Remember me");
    private final JLabel errorLabel = new JLabel();

    public LoginView(LoginViewModel loginViewModel, LoginController loginController) {
        super("log in");
        this.loginViewModel = loginViewModel;
        this.loginController = loginController;
        this.loginViewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout());
        setBackground(UiConstants.PRIMARY_COLOUR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("Sign in");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(UiConstants.PRIMARY_COLOUR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("or ");
        subLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subLabel.setForeground(UiConstants.SECONDARY_COLOUR);
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(subLabel, gbc);

        gbc.gridx = 1;
        panel.add(signUpButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(20), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Username", usernameField), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Password", passwordField), gbc);

        gbc.gridy++;
        panel.add(rememberMeBox, gbc);

        gbc.gridy++;
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        errorLabel.setVisible(false);
        panel.add(errorLabel, gbc);

        gbc.gridy++;
        loginButton.setPreferredSize(new Dimension(0, 40));
        loginButton.setBackground(UiConstants.PRIMARY_COLOUR);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(loginButton, gbc);

        gbc.gridy++;

        add(panel);
        wireListeners();
    }

    private JPanel createFormPanel() {
        // No longer used; replaced by createFieldPanel for modern layout
        return null;
    }

    private void wireListeners() {
        loginButton.addActionListener(evt -> {
            LoginState loginState = loginViewModel.getState();
            loginController.execute(usernameField.getText(), new String(passwordField.getPassword()));
        });

        signUpButton.addActionListener(evt -> loginController.switchToSignupView());

        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                LoginState loginState = loginViewModel.getState();
                loginState.setUsername(usernameField.getText());
                loginViewModel.setState(loginState);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                LoginState loginState = loginViewModel.getState();
                loginState.setPassword(new String(passwordField.getPassword()));
                loginViewModel.setState(loginState);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            errorLabel.setText(state.getUsernameError());
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
        }
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        errorLabel.setVisible(false);
        LoginState newState = new LoginState();
        loginViewModel.setState(newState);

    }

    private JPanel createFieldPanel(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lbl.setForeground(Color.DARK_GRAY);
        panel.add(lbl, BorderLayout.NORTH);
        field.setFont(new Font("SansSerif", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new javax.swing.border.EmptyBorder(0, 8, 0, 0)));
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(0, 36));
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

}
