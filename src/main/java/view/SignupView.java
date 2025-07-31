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
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

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
            // Get the current password field values directly when signing up
            signupState.setPassword(new String(passwordField.getPassword()));
            signupState.setRepeatPassword(new String(confirmPasswordField.getPassword()));
            signupState.setUsername(usernameField.getText());
            signupViewModel.setState(signupState);
            signupController.execute(
                signupState.getUsername(), 
                signupState.getPassword(), 
                signupState.getRepeatPassword()
            );
        });

        loginButton.addActionListener(e -> signupController.switchToLoginView());

        // Replace KeyListeners with DocumentListeners for better text input handling
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateState() {
                SignupState signupState = signupViewModel.getState();
                signupState.setUsername(usernameField.getText());
                signupViewModel.setState(signupState);
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateState(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateState(); }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        } else if (state.getPasswordError() != null) {
            JOptionPane.showMessageDialog(this, state.getPasswordError());
        }
    }
}
