package view;

import entity.LeaderboardEntry;
import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardState;
import interface_adapter.leaderboard.LeaderboardViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LeaderboardView extends BaseView {
    private final LeaderboardViewModel leaderboardViewModel;
    private final DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;

    public LeaderboardView(LeaderboardViewModel leaderboardViewModel,
            LeaderboardController leaderboardController) {
        super("leaderboard");
        this.leaderboardViewModel = leaderboardViewModel;
        // this.leaderboardController = leaderboardController; // Removed unused field

        // Initialize table model
        String[] columnNames = { "Rank", "Username", "Portfolio", "Total Value" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Use modern layout similar to Login/Signup views
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 60, 120)); // PRIMARY_COLOUR

        // Create main white panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        // Title
        JLabel titleLabel = new JLabel("Leaderboard");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 60, 120));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(10), gbc);

        // Description
        JLabel descriptionLabel = new JLabel("Compare your portfolio performance with other users.");
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        descriptionLabel.setForeground(Color.DARK_GRAY);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy++;
        mainPanel.add(descriptionLabel, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // Refresh button
        JButton refreshButton = new JButton("Refresh Leaderboard");
        refreshButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        refreshButton.setBackground(new Color(30, 60, 120));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        refreshButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(180, 40)); // Make it less wide
        refreshButton.addActionListener(e -> leaderboardController.execute());
        
        // Add hover effect
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(40, 70, 130));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(30, 60, 120));
            }
        });

        gbc.gridy++;
        mainPanel.add(refreshButton, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // Modern table
        JPanel tableSection = createModernTableSection();
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(tableSection, gbc);

        // Add main panel to a scroll pane for overflow handling
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(new Color(30, 60, 120)); // UiConstants.PRIMARY_COLOUR
        scrollPane.getViewport().setBackground(new Color(30, 60, 120));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Add scroll pane to view with proper resizing
        GridBagConstraints viewGbc = new GridBagConstraints();
        viewGbc.fill = GridBagConstraints.BOTH;
        viewGbc.weightx = 1.0;
        viewGbc.weighty = 1.0;
        this.add(scrollPane, viewGbc);

        // Set up listeners
        setupListeners();

        // Load leaderboard data when view is created
        leaderboardController.execute();
    }

    private JPanel createModernTableSection() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(245, 245, 245));
        container.setOpaque(true);

        // Create styled table
        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(32);
        table.setBackground(new Color(245, 245, 245));
        table.setForeground(Color.DARK_GRAY);
        table.setSelectionBackground(new Color(230, 240, 250));
        table.setSelectionForeground(Color.DARK_GRAY);
        table.setGridColor(new Color(220, 220, 220)); // Lighter grid lines for a modern look
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
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(30, 60, 120));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBackground(new Color(245, 245, 245));
        tableScrollPane.getViewport().setBackground(new Color(245, 245, 245));
        tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        container.add(tableScrollPane, BorderLayout.CENTER);
        return container;
    }

    private void setupListeners() {
        leaderboardViewModel.addPropertyChangeListener(evt -> {
            LeaderboardState state = leaderboardViewModel.getState();
            updateLeaderboard(state.getLeaderboardEntries());
        });
    }

    private void updateLeaderboard(List<LeaderboardEntry> entries) {
        // Clear existing data
        tableModel.setRowCount(0);

        if (entries != null && !entries.isEmpty()) {
            // Add entries to table
            for (LeaderboardEntry entry : entries) {
                Object[] rowData = {
                        entry.getRank(),
                        entry.getUsername(),
                        entry.getPortfolioName(),
                        String.format("$%.2f", entry.getTotalValue())
                };
                tableModel.addRow(rowData);
            }
        }

        // Table automatically repaints when model changes
    }
}
