package view.dashboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import view.ui.UiConstants;

/**
 * Welcome section panel for the dashboard.
 */
public class WelcomePanel extends JPanel {

    public WelcomePanel() {
        super(new GridBagLayout());
        setBackground(UiConstants.Colors.CANVAS_BG);
        setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel welcomeLabel = new JLabel("Welcome to Fundi!");
        welcomeLabel.setFont(UiConstants.Fonts.HEADING);
        welcomeLabel.setForeground(UiConstants.Colors.PRIMARY);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(welcomeLabel, gbc);
    }
}
