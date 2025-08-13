package view.dashboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interfaceadapter.main.MainState;
import interfaceadapter.main.MainViewModel;
import view.ui.UiConstants;

/**
 * Displays and updates the username label when MainViewModel changes.
 */
public class UsernamePanel extends JPanel {
    private final JLabel usernameLabel;

    public UsernamePanel(MainViewModel mainViewModel) {
        super(new GridBagLayout());
        setBackground(UiConstants.Colors.CANVAS_BG);
        setOpaque(true);

        usernameLabel = new JLabel();
        usernameLabel.setFont(UiConstants.Fonts.BODY);
        usernameLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        mainViewModel.addPropertyChangeListener(evt -> update(mainViewModel.getState()));
        update(mainViewModel.getState());
    }

    private void update(MainState state) {
        if (state != null && state.getUsername() != null && !state.getUsername().isEmpty()) {
            usernameLabel.setText("Logged in as: " + state.getUsername());
        } else {
            usernameLabel.setText("");
        }
    }
}
