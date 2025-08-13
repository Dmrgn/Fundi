package view.dashboard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.util.List;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import entity.SearchResult;
import view.ui.ButtonFactory;
import view.ui.UiConstants;

/**
 * Displays search results in a scrollable list and emits selection events.
 */
public class SearchResultsPanel extends JPanel {
    public interface Listener {
        void onClear();

        void onOpenDetails(String symbol);
    }

    private final JPanel contentPanel;
    private final JScrollPane scrollPane;

    public SearchResultsPanel(Listener listener) {
        super(new BorderLayout());
        setBackground(UiConstants.Colors.CANVAS_BG);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1),
                BorderFactory.createEmptyBorder(UiConstants.Spacing.LG, UiConstants.Spacing.LG,
                        UiConstants.Spacing.LG, UiConstants.Spacing.LG)));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UiConstants.Colors.CANVAS_BG);
        JLabel title = new JLabel("Search Results:");
        title.setFont(UiConstants.Fonts.HEADING);
        title.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        JButton clearButton = ButtonFactory.createSecondaryButton("Clear");
        clearButton.setPreferredSize(new Dimension(80, 28));
        clearButton.addActionListener(e -> {
            if (listener != null)
                listener.onClear();
        });
        header.add(title, BorderLayout.WEST);
        header.add(clearButton, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        contentPanel.add(Box.createVerticalStrut(UiConstants.Spacing.SM));

        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(480, 220));

        add(scrollPane, BorderLayout.CENTER);
    }

    public void setResults(List<SearchResult> results, Listener listener) {
        contentPanel.removeAll();
        boolean addedAny = false;
        if (results != null) {
            for (SearchResult r : results.stream()
                    .filter(Objects::nonNull)
                    .filter(r -> r.getSymbol() != null && !r.getSymbol().isEmpty())
                    .filter(r -> r.getName() != null && !r.getName().isEmpty())
                    .limit(10)
                    .toList()) {
                JButton btn = ButtonFactory.createOutlinedButton(r.getSymbol() + " - " + r.getName());
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.addActionListener(e -> {
                    if (listener != null)
                        listener.onOpenDetails(r.getSymbol());
                });
                contentPanel.add(btn);
                contentPanel.add(Box.createVerticalStrut(5));
                addedAny = true;
            }
        }
        if (!addedAny) {
            JLabel empty = new JLabel("No results found");
            empty.setFont(UiConstants.Fonts.SMALL);
            empty.setForeground(UiConstants.Colors.TEXT_MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(empty);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
