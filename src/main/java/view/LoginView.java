package view;

import interfaceadapter.login.LoginController;
import interfaceadapter.login.LoginState;
import interfaceadapter.login.LoginViewModel;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
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

        // Header
        JLabel title = new JLabel("Sign in");
        title.setFont(UiConstants.Fonts.TITLE);
        title.setForeground(Color.WHITE);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(title);
        header.add(headerLeft, BorderLayout.WEST);

        // Main panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UiConstants.Colors.CANVAS_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        gbc.gridwidth = 2;
        panel.add(signUpButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(UiConstants.Spacing.XL), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Username", usernameField), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Password", passwordField), gbc);

        gbc.gridy++;
        rememberMeBox.setFont(UiConstants.Fonts.BODY);
        rememberMeBox.setOpaque(false);
        panel.add(rememberMeBox, gbc);

        gbc.gridy++;
        errorLabel.setForeground(UiConstants.Colors.DANGER);
        errorLabel.setFont(UiConstants.Fonts.SMALL);
        errorLabel.setVisible(false);
        panel.add(errorLabel, gbc);

        gbc.gridy++;
        loginButton.setPreferredSize(new Dimension(0, 40));
        panel.add(loginButton, gbc);

        gbc.gridy++;
        panel.add(signUpButton, gbc);

        // Add to BaseView content within scroll pane
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.setBackground(UiConstants.Colors.CANVAS_BG);
        scroll.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        content.add(scroll, BorderLayout.CENTER);

        wireListeners();
    }

    private void wireListeners() {
        loginButton.addActionListener(evt -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            loginController.execute(username, password);

            boolean rememberMe = rememberMeBox.isSelected();
            loginController.saveRememberMe(username, rememberMe);
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
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(UiConstants.Colors.CANVAS_BG);
        JLabel lbl = new JLabel(label);
        lbl.setFont(UiConstants.Fonts.BODY);
        lbl.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        p.add(lbl, BorderLayout.NORTH);
        field.setFont(UiConstants.Fonts.FORM);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new javax.swing.border.EmptyBorder(0, 8, 0, 0)));
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(0, 36));
        p.add(field, BorderLayout.CENTER);
        return p;
    }
}
