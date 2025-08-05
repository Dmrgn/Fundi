package view;

import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardState;
import interface_adapter.leaderboard.LeaderboardViewModel;
import entity.LeaderboardEntry;
import view.components.UiFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.List;

public class LeaderboardView extends BaseView implements ActionListener, PropertyChangeListener {
    private final LeaderboardViewModel leaderboardViewModel;
    private final LeaderboardController leaderboardController;

    private final JButton refreshButton;
    private final JTable leaderboardTable;
    private final DefaultTableModel tableModel;
    private final JLabel errorLabel;
    private final DecimalFormat currencyFormat;

    public LeaderboardView(LeaderboardViewModel leaderboardViewModel, 
                          LeaderboardController leaderboardController) {
        super("leaderboard");
        this.leaderboardViewModel = leaderboardViewModel;
        this.leaderboardController = leaderboardController;
        this.leaderboardViewModel.addPropertyChangeListener(this);

        this.currencyFormat = new DecimalFormat("$#,##0.00");
        this.refreshButton = new JButton(LeaderboardViewModel.REFRESH_BUTTON_LABEL);
        this.errorLabel = new JLabel();

        // Set up table
        String[] columnNames = {"Rank", "Portfolio", "User", "Portfolio Value"};
        this.tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        this.leaderboardTable = new JTable(tableModel);
        this.leaderboardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setupLayout();
        
        // Add action listeners
        refreshButton.addActionListener(this);
        
        // Load initial data
        leaderboardController.execute();
    }

    private void setupLayout() {
        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BorderLayout());

        this.add(contentPanel, BorderLayout.CENTER);

        // Top panel with title and refresh button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JPanel titlePanel = UiFactory.createTitlePanel(LeaderboardViewModel.TITLE_LABEL);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);
        
        topPanel.add(titlePanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Center panel with table
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Bottom panel for error messages
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add to content panel
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(errorLabel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshButton) {
            // Show loading state
            LeaderboardState state = leaderboardViewModel.getState();
            state.setLoading(true);
            leaderboardViewModel.setState(state);
            leaderboardViewModel.firePropertyChanged();
            
            // Execute refresh
            leaderboardController.execute();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            LeaderboardState state = (LeaderboardState) evt.getNewValue();
            updateView(state);
        }
    }

    private void updateView(LeaderboardState state) {
        // Clear error message
        errorLabel.setText("");

        // Handle loading state
        if (state.isLoading()) {
            refreshButton.setEnabled(false);
            refreshButton.setText("Loading...");
            return;
        } else {
            refreshButton.setEnabled(true);
            refreshButton.setText(LeaderboardViewModel.REFRESH_BUTTON_LABEL);
        }

        // Handle error state
        if (state.getError() != null) {
            errorLabel.setText(state.getError());
            return;
        }

        // Update table with leaderboard data
        List<LeaderboardEntry> entries = state.getLeaderboardEntries();
        if (entries != null) {
            updateTable(entries);
        }
    }

    private void updateTable(List<LeaderboardEntry> entries) {
        // Clear existing data
        tableModel.setRowCount(0);

        // Add new data
        for (LeaderboardEntry entry : entries) {
            Object[] row = {
                entry.getRank(),
                entry.getPortfolioName(),
                entry.getUsername(),
                currencyFormat.format(entry.getTotalValue())
            };
            tableModel.addRow(row);
        }

        // Refresh the table
        tableModel.fireTableDataChanged();
    }
}
