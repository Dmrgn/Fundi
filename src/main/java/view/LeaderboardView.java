package view;

import entity.LeaderboardEntry;
import interfaceadapter.leaderboard.LeaderboardController;
import interfaceadapter.leaderboard.LeaderboardState;
import interfaceadapter.leaderboard.LeaderboardViewModel;

import entity.CurrencyConverter;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import view.ui.UiConstants;

import static entity.PreferredCurrencyManager.getConverter;
import static entity.PreferredCurrencyManager.getPreferredCurrency;

public class LeaderboardView extends BaseView {
    private final LeaderboardViewModel leaderboardViewModel;
    private final DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;
    private boolean loadedOnce = false;

    public LeaderboardView(LeaderboardViewModel leaderboardViewModel,
            LeaderboardController leaderboardController) {
        super("leaderboard");
        this.leaderboardViewModel = leaderboardViewModel;

        // Initialize table model
        String[] columnNames = { "Rank", "Username", "Portfolio", "Total Value" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Header
        JLabel titleLabel = new JLabel("Leaderboard");
        titleLabel.setFont(UiConstants.Fonts.TITLE);
        titleLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(titleLabel);
        header.add(headerLeft, BorderLayout.WEST);

        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        // Title (in content area subtle repeat or description)
        JLabel descriptionLabel = new JLabel("Compare your portfolio performance with other users.");
        descriptionLabel.setFont(UiConstants.Fonts.BODY);
        descriptionLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(descriptionLabel, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.LG), gbc);

        // Refresh button
        JButton refreshButton = new JButton("Refresh Leaderboard");
        refreshButton.setFont(UiConstants.Fonts.BUTTON);
        refreshButton.setBackground(UiConstants.Colors.PRIMARY);
        refreshButton.setForeground(UiConstants.Colors.ON_PRIMARY);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        refreshButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(180, 40));
        refreshButton.addActionListener(e -> leaderboardController.execute());

        // Hover effect
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(UiConstants.PRESSED_COLOUR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(UiConstants.Colors.PRIMARY);
            }
        });

        gbc.gridy++;
        mainPanel.add(refreshButton, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.LG), gbc);

        // Modern table
        JPanel tableSection = createModernTableSection();
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(tableSection, gbc);

        // Add main panel to a scroll pane for overflow handling
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        content.add(scrollPane, BorderLayout.CENTER);

        // Set up listeners
        setupListeners();

        // Remove the eager load here to avoid double fetch
        // leaderboardController.execute();

        // Load once when the view is first shown
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                if (!loadedOnce) {
                    loadedOnce = true;
                    leaderboardController.execute();
                }
            }
        });
    }

    private JPanel createModernTableSection() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(UiConstants.Colors.CANVAS_BG);
        container.setOpaque(true);

        // Create styled table
        JTable table = new JTable(tableModel);
        table.setFont(UiConstants.Fonts.BODY);
        table.setRowHeight(32);
        table.setBackground(UiConstants.Colors.CANVAS_BG);
        table.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        table.setSelectionBackground(new Color(230, 240, 250));
        table.setSelectionForeground(UiConstants.Colors.TEXT_PRIMARY);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setFillsViewportHeight(true);

        // Center-align text, zebra striping, and highlight top 3 rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel label) {
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                }
                if (!isSelected) {
                    Color bg;
                    if (row == 0) {
                        bg = new Color(255, 249, 196); // subtle gold for rank 1
                    } else if (row == 1) {
                        bg = new Color(236, 239, 241); // light silver for rank 2
                    } else if (row == 2) {
                        bg = new Color(255, 243, 224); // light bronze for rank 3
                    } else {
                        bg = (row % 2 == 0) ? new Color(250, 250, 250) : new Color(242, 246, 252);
                    }
                    c.setBackground(bg);
                }
                return c;
            }
        });

        // Style the header
        table.getTableHeader().setFont(UiConstants.Fonts.BUTTON);
        table.getTableHeader().setBackground(UiConstants.Colors.PRIMARY);
        table.getTableHeader().setForeground(UiConstants.Colors.ON_PRIMARY);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        tableScrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(UiConstants.Colors.BORDER_MUTED, 1));

        container.add(tableScrollPane, BorderLayout.CENTER);
        return container;
    }

    private void setupListeners() {
        leaderboardViewModel.addPropertyChangeListener(evt -> {
            LeaderboardState state = leaderboardViewModel.getState();
            // Ensure updates happen on EDT to avoid refresh issues
            SwingUtilities.invokeLater(() -> updateLeaderboard(state.getLeaderboardEntries()));
        });
    }

    private void updateLeaderboard(List<LeaderboardEntry> entries) {
        // Clear existing data
        tableModel.setRowCount(0);

        CurrencyConverter converter = getConverter();
        String preferredCurrency = getPreferredCurrency();

        if (entries != null && !entries.isEmpty()) {
            // Add entries to table
            for (LeaderboardEntry entry : entries) {
                double originalValue = entry.getTotalValue();
                double convertedPrice = originalValue;
                if (converter != null && !preferredCurrency.equals("USD")) {
                    try {
                        convertedPrice = converter.convert(originalValue, "USD", preferredCurrency);
                    } catch (Exception e) {
                        System.err.println("Currency conversion failed in leaderboard: " + e.getMessage());
                    }
                }
                Object[] rowData = {
                        entry.getRank(),
                        entry.getUsername(),
                        entry.getPortfolioName(),
                        String.format("%.2f %s", convertedPrice, preferredCurrency)
                };
                tableModel.addRow(rowData);
            }
        }
        // Table automatically repaints when model changes
    }
}
