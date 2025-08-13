package view.dashboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.function.Consumer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import view.ui.UiConstants;

/**
 * Search section panel for the dashboard.
 */
public class SearchSectionPanel extends JPanel {

    public SearchSectionPanel(Consumer<String> onSearch) {
        super(new GridBagLayout());
        setBackground(UiConstants.Colors.CANVAS_BG);
        setOpaque(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM, UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel searchTitle = new JLabel("Search Stocks");
        searchTitle.setFont(UiConstants.Fonts.HEADING);
        searchTitle.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        searchTitle.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(searchTitle, gbc);

        gbc.gridy++;
        SearchBarPanel searchBar = new SearchBarPanel(onSearch::accept);
        add(searchBar, gbc);
    }
}
