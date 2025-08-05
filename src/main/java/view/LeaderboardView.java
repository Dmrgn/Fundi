package view;

import entity.LeaderboardEntry;
import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardState;
import interface_adapter.leaderboard.LeaderboardViewModel;
import view.ui.PanelFactory;
import view.ui.TableFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LeaderboardView extends BaseView {
    private final LeaderboardViewModel leaderboardViewModel;
    private final LeaderboardController leaderboardController;
    private final DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;

    public LeaderboardView(LeaderboardViewModel leaderboardViewModel,
            LeaderboardController leaderboardController) {
        super("leaderboard");
        this.leaderboardViewModel = leaderboardViewModel;
        this.leaderboardController = leaderboardController;

        // Initialize table model
        String[] columnNames = { "Rank", "Username", "Portfolio", "Total Value" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(contentPanel, BorderLayout.CENTER);

        // Title
        contentPanel.add(PanelFactory.createTitlePanel("Leaderboard"));
        contentPanel.add(Box.createVerticalStrut(30));

        JLabel descriptionLabel = new JLabel("Compare your portfolio performance with other users.");
        descriptionLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(200, 200, 200));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(descriptionLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Add Refresh Button
        JButton refreshButton = new JButton("Refresh Leaderboard");
        refreshButton.setFont(new Font("Sans Serif", Font.BOLD, 14));
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        refreshButton.addActionListener(e -> leaderboardController.execute()); // Trigger refresh

        contentPanel.add(refreshButton);
        contentPanel.add(Box.createVerticalStrut(20));

        // Create table using TableFactory
        tableScrollPane = TableFactory.createStyledTable(tableModel);
        tableScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Customize table for dark theme
        JTable table = (JTable) tableScrollPane.getViewport().getView();
        table.setBackground(new Color(45, 45, 45));
        table.setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(70, 70, 70));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(30, 60, 120));
        table.getTableHeader().setForeground(Color.WHITE);

        // Set scroll pane background
        tableScrollPane.getViewport().setBackground(new Color(45, 45, 45));
        tableScrollPane.setBackground(new Color(45, 45, 45));

        contentPanel.add(tableScrollPane);
        contentPanel.add(Box.createVerticalGlue());

        // Set up listeners
        setupListeners();

        // Load leaderboard data when view is created
        leaderboardController.execute();
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
