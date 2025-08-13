package view.dashboard;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.UiConstants;

/**
 * A reusable search bar panel with a text field and a search button.
 * Emits a callback when the user triggers a search.
 */
public class SearchBarPanel extends JPanel {
    public interface Listener {
        void onSearch(String query);
    }

    private final JTextField searchField;
    private final JButton searchButton;

    public SearchBarPanel(Listener listener) {
        super(new GridBagLayout());
        setBackground(UiConstants.Colors.CANVAS_BG);
        setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        searchField = FieldFactory.createSearchField("Search symbols or companies");
        searchField.setPreferredSize(new Dimension(200, 36));
        searchField.setMaximumSize(new Dimension(200, 36));

        // Placeholder behavior
        searchField.setText("Search symbols or companies");
        searchField.setForeground(UiConstants.Colors.TEXT_MUTED);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search symbols or companies")) {
                    searchField.setText("");
                    searchField.setForeground(UiConstants.Colors.TEXT_PRIMARY);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search symbols or companies");
                    searchField.setForeground(UiConstants.Colors.TEXT_MUTED);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        add(searchField, gbc);

        searchButton = ButtonFactory.createPrimaryButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 36));
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.LG, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
        add(searchButton, gbc);

        Runnable doSearch = () -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty() && !"Search symbols or companies".equals(query)) {
                if (listener != null)
                    listener.onSearch(query);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search query.");
            }
        };

        searchButton.addActionListener(e -> doSearch.run());
        searchField.addActionListener(e -> doSearch.run());
    }
}
