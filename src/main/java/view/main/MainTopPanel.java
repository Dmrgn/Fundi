package view.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import interfaceadapter.main.MainViewModel;
import interfaceadapter.search.SearchController;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.UiConstants;

public class MainTopPanel extends JPanel {

    private static final int SEARCH_FIELD_MIN_WIDTH = 200;
    private static final int SEARCH_FIELD_PREF_WIDTH = 250;
    private static final int SEARCH_FIELD_HEIGHT = 36;
    private static final int SEARCH_BUTTON_WIDTH = 100;

    public MainTopPanel(MainViewModel mainViewModel, SearchController searchController) {
        super(new GridBagLayout());
        setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel welcomeTitle = new JLabel("Welcome to Fundi!");
        welcomeTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        welcomeTitle.setForeground(UiConstants.Colors.ON_PRIMARY);
        welcomeTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(welcomeTitle, gbc);

        gbc.gridy++;
        add(Box.createVerticalStrut(UiConstants.Spacing.LG), gbc);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(UiConstants.Fonts.BODY);
        // Use a text color intended for foreground text
        usernameLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Initialize immediately if username is already present
        if (mainViewModel.getState() != null
                && mainViewModel.getState().getUsername() != null
                && !mainViewModel.getState().getUsername().isBlank()) {
            usernameLabel.setText("Logged in as: " + mainViewModel.getState().getUsername());
        }
        // Keep updated on any state change
        mainViewModel.addPropertyChangeListener(evt -> {
            if (mainViewModel.getState() != null) {
                String u = mainViewModel.getState().getUsername();
                if (u == null || u.isBlank()) {
                    usernameLabel.setText("");
                } 
                else {
                    usernameLabel.setText("Logged in as: " + u);
                }
            }
        });
        gbc.gridy++;
        JTextField searchField = FieldFactory.createSearchField("Search symbols or companies");
        searchField.setMinimumSize(new Dimension(SEARCH_FIELD_MIN_WIDTH, SEARCH_FIELD_HEIGHT));
        searchField.setPreferredSize(new Dimension(SEARCH_FIELD_PREF_WIDTH, SEARCH_FIELD_HEIGHT));
        JButton searchButton = ButtonFactory.createPrimaryButton("Search");
        searchButton.setPreferredSize(new Dimension(SEARCH_BUTTON_WIDTH, SEARCH_FIELD_HEIGHT));
        gbc.gridy++;
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        add(searchField, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        add(searchButton, gbc);
        gbc.weightx = 1.0;
        add(searchField, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        add(searchButton, gbc);
        Runnable doSearch = () -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                searchController.execute(query);
            } 
            else {
                JOptionPane.showMessageDialog(this, "Please enter a search query.");
            }
        };
        searchButton.addActionListener(evt -> doSearch.run());
        searchField.addActionListener(evt -> doSearch.run());
    }
}
