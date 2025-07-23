package view;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//import interface_adapter.change_password.ChangePasswordController;
//import interface_adapter.change_password.LoggedInState;
//import interface_adapter.change_password.LoggedInViewModel;
//import interface_adapter.logout.LogoutController;
import interface_adapter.history.HistoryState;
import interface_adapter.history.HistoryViewModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.portfolios.PortfoliosController;

/**
 * The View for when the user is looking their history for a portfolio in the program.
 */
public class HistoryView extends JPanel {

    private final String viewName = "history";
    private final HistoryViewModel historyViewModel;

    public HistoryView(HistoryViewModel historyViewModel) {
        this.historyViewModel = historyViewModel;
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === 1. Top panel with plain text intro ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Portfolio History:");
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(welcomeLabel);
        topPanel.add(Box.createVerticalStrut(10));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout(10, 10));

        String[] columnNames = {"Ticker", "Quantity", "Price", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 600));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        historyViewModel.addPropertyChangeListener(evt -> {
            HistoryState state = historyViewModel.getState();
            String[] tickers = state.getTickers();
            int[] amounts = state.getAmounts();
            double[] prices = state.getPrices();
            LocalDate[] dates = state.getDates();
            tableModel.setRowCount(0);

            for (int i = 0; i < tickers.length; i++) {
                tableModel.addRow(new Object[]{tickers[i], amounts[i], prices[i], dates[i]});
            }
        });


        // === 3. Buttons ===
        JPanel bottomPanel = new JPanel();

        JButton useCaseButton = new JButton("Back");
        HistoryState state = historyViewModel.getState();

        useCaseButton.addActionListener(evt -> {

        });
        bottomPanel.add(useCaseButton, BorderLayout.CENTER);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add to main layout
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return viewName;
    }
}