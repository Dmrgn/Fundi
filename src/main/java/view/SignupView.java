package view;

import interface_adapter.signup.SignupViewModel;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupController;
import view.components.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends BaseView implements PropertyChangeListener {
    private final SignupViewModel signupViewModel;
    private final SignupController signupController;
    private final JTextField usernameField = UIFactory.createTextField();
    private final JPasswordField passwordField = UIFactory.createPasswordField();
    private final JPasswordField confirmPasswordField = UIFactory.createPasswordField();
    private final JButton loginButton = UIFactory.createStyledButton("Login");
    private final JButton signUpButton = UIFactory.createStyledButton("Sign Up");


    public SignupView(SignupViewModel signupViewModel, SignupController signupController) {
        super("signup");
        this.signupViewModel = signupViewModel;
        this.signupController = signupController;
        signupViewModel.addPropertyChangeListener(this);

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = UIFactory.createTitlePanel("Signup Screen");
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = UIFactory.createButtonPanel(signUpButton, loginButton);

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

        JPanel usernameInfo = UIFactory.createFormPanel("Username", usernameField);
        JPanel passwordInfo = UIFactory.createFormPanel("Password", passwordField);
        JPanel confirmPasswordInfo = UIFactory.createFormPanel("Confirm", confirmPasswordField);

        form.add(usernameInfo);
        form.add(Box.createVerticalStrut(10));
        form.add(passwordInfo);
        form.add(Box.createVerticalStrut(10));
        form.add(confirmPasswordInfo);

        return form;
    }

    private void wireListeners() {
        signUpButton.addActionListener(e -> {
            SignupState signupState = signupViewModel.getState();
            signupController.execute(signupState.getUsername(), signupState.getPassword(), signupState.getRepeatPassword());
        });

        loginButton.addActionListener(e -> signupController.switchToLoginView());

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

        passwordField.addKeyListener(new KeyAdapter() {
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
        SignupState state = ((SignupViewModel) evt.getSource()).getState();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        } else if (state.getPasswordError() != null) {
            JOptionPane.showMessageDialog(this, state.getPasswordError());
        }
    }
}
