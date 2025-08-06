package view;

import interface_adapter.signup.SignupViewModel;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupController;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.PanelFactory;
import view.ui.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for the Signup Use Case
 */
public class SignupView extends BaseView implements PropertyChangeListener {
    private final SignupViewModel signupViewModel;
    private final SignupController signupController;
    private final JTextField usernameField = FieldFactory.createTextField();
    private final JPasswordField passwordField = FieldFactory.createPasswordField();
    private final JPasswordField confirmPasswordField = FieldFactory.createPasswordField();
    private final JButton loginButton = ButtonFactory.createStyledButton("Login");
    private final JButton signUpButton = ButtonFactory.createStyledButton("Sign Up");

    public SignupView(SignupViewModel signupViewModel, SignupController signupController) {
        super("signup");
        this.signupViewModel = signupViewModel;
        this.signupController = signupController;
        signupViewModel.addPropertyChangeListener(this);

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = PanelFactory.createTitlePanel("Signup Screen");
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = ButtonFactory.createButtonPanel(signUpButton, loginButton);

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titlePanel);
        contentPanel.add(UiConstants.bigVerticalGap());
        contentPanel.add(formPanel);
        contentPanel.add(UiConstants.mediumVerticalGap());
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        this.add(contentPanel, BorderLayout.CENTER);

        wireListeners();
    }


    private JPanel createFormPanel() {
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JPanel usernameInfo = PanelFactory.createFormPanel("Username", usernameField);
        JPanel passwordInfo = PanelFactory.createFormPanel("Password", passwordField);
        JPanel confirmPasswordInfo = PanelFactory.createFormPanel("Confirm", confirmPasswordField);

        form.add(usernameInfo);
        form.add(UiConstants.mediumVerticalGap());
        form.add(passwordInfo);
        form.add(UiConstants.mediumVerticalGap());
        form.add(confirmPasswordInfo);

        return form;
    }

    private void wireListeners() {
        signUpButton.addActionListener(evt -> {
            SignupState signupState = signupViewModel.getState();
            signupController.execute(signupState.getUsername(), signupState.getPassword(),
                    signupState.getRepeatPassword());
        });

        loginButton.addActionListener(evt -> signupController.switchToLoginView());

        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SignupState signupState = signupViewModel.getState();
                signupState.setUsername(usernameField.getText() + e.getKeyChar());
                signupViewModel.setState(signupState);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SignupState signupState = signupViewModel.getState();
                signupState.setPassword(new String(passwordField.getPassword()) + e.getKeyChar());
                signupViewModel.setState(signupState);
            }
        });

        confirmPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SignupState signupState = signupViewModel.getState();
                signupState.setRepeatPassword(new String(confirmPasswordField.getPassword()) + e.getKeyChar());
                signupViewModel.setState(signupState);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }

        else if (state.getPasswordError() != null) {
            JOptionPane.showMessageDialog(this, state.getPasswordError());
        }
    }
}
