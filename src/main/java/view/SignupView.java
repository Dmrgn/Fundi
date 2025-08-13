package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import interfaceadapter.signup.SignupController;
import interfaceadapter.signup.SignupState;
import interfaceadapter.signup.SignupViewModel;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.UiConstants;

/**
 * View for the Signup Use Case.
 */
public class SignupView extends AbstractBaseView implements PropertyChangeListener {
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

        // Header
        JLabel title = new JLabel("Sign up");
        title.setFont(UiConstants.Fonts.TITLE);
        title.setForeground(Color.WHITE);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(title);
        getHeader().add(headerLeft, BorderLayout.WEST);

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

        gbc.gridy++;
        panel.add(Box.createVerticalStrut(UiConstants.Spacing.XL), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Username", usernameField), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Password", passwordField), gbc);

        gbc.gridy++;
        panel.add(createFieldPanel("Confirm Password", confirmPasswordField), gbc);

        gbc.gridy++;
        errorLabel.setForeground(UiConstants.Colors.DANGER);
        errorLabel.setFont(UiConstants.Fonts.SMALL);
        errorLabel.setVisible(false);
        panel.add(errorLabel, gbc);

        gbc.gridy++;
        signUpButton.setPreferredSize(new Dimension(0, 40));
        panel.add(signUpButton, gbc);

        gbc.gridy++;
        panel.add(loginButton, gbc);

        // Add to BaseView content within scroll pane
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.setBackground(UiConstants.Colors.CANVAS_BG);
        scroll.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        getContent().add(scroll, BorderLayout.CENTER);

        wireListeners();
    }

    private void wireListeners() {
        signUpButton.addActionListener(evt -> {
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
        }
        else if (state.getPasswordError() != null) {
            errorLabel.setText(state.getPasswordError());
            errorLabel.setVisible(true);
        }
        else {
            errorLabel.setVisible(false);
        }

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
