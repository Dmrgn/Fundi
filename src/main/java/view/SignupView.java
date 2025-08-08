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
    private final JButton signUpButton = ButtonFactory.createPrimaryButton("Sign up");
    private final JButton loginButton = ButtonFactory.createLinkButton("Already have an account?");
    private final JLabel errorLabel = new JLabel();

    public SignupView(SignupViewModel signupViewModel, SignupController signupController) {
        super("signup");
        this.signupViewModel = signupViewModel;
        this.signupController = signupController;
        signupViewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout());
        setBackground(UiConstants.PRIMARY_COLOUR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("Sign up");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(UiConstants.PRIMARY_COLOUR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        panel.add(Box.createVerticalStrut(20), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Username", usernameField), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Password", passwordField), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Confirm Password", confirmPasswordField), gbc);

        gbc.gridy++;
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        errorLabel.setVisible(false);
        panel.add(errorLabel, gbc);

        gbc.gridy++;
        signUpButton.setPreferredSize(new Dimension(0, 40));
        signUpButton.setBackground(UiConstants.PRIMARY_COLOUR);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(signUpButton, gbc);

        gbc.gridy++;
        panel.add(loginButton, gbc);

        add(panel);
        wireListeners();
    }

    private JPanel createFormPanel() {
        // No longer used; replaced by createFieldPanel for modern layout
        return null;
    }

    private void wireListeners() {
        signUpButton.addActionListener(evt -> {
            SignupState signupState = signupViewModel.getState();
            signupController.execute(usernameField.getText(), new String(passwordField.getPassword()),
                    new String(confirmPasswordField.getPassword()));
        });

        loginButton.addActionListener(evt -> signupController.switchToLoginView());

        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                SignupState signupState = signupViewModel.getState();
                signupState.setUsername(usernameField.getText());
                signupViewModel.setState(signupState);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                SignupState signupState = signupViewModel.getState();
                signupState.setPassword(new String(passwordField.getPassword()));
                signupViewModel.setState(signupState);
            }
        });

        confirmPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                SignupState signupState = signupViewModel.getState();
                signupState.setRepeatPassword(new String(confirmPasswordField.getPassword()));
                signupViewModel.setState(signupState);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            errorLabel.setText(state.getUsernameError());
            errorLabel.setVisible(true);
        } else if (state.getPasswordError() != null) {
            errorLabel.setText(state.getPasswordError());
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
        }

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
