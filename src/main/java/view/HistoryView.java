package view;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//import interface_adapter.change_password.ChangePasswordController;
//import interface_adapter.change_password.LoggedInState;
//import interface_adapter.change_password.LoggedInViewModel;
//import interface_adapter.logout.LogoutController;
import interface_adapter.history.HistoryController;
import interface_adapter.history.HistoryState;
import interface_adapter.history.HistoryViewModel;
import view.components.UIFactory;

/**
 * The View for when the user is looking their history for a portfolio in the
 * program.
 */
public class HistoryView extends BaseView {

    private final HistoryViewModel historyViewModel;
    private final HistoryController historyController;
    private final interface_adapter.navigation.NavigationController navigationController;
    private static final String[] columnNames = { "Ticker", "Quantity", "Price", "Date" };
    private final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public HistoryView(HistoryViewModel historyViewModel, HistoryController historyController,
            interface_adapter.navigation.NavigationController navigationController) {
        super("history");
        this.historyViewModel = historyViewModel;
        this.historyController = historyController;
        this.navigationController = navigationController;

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(createBackButtonPanel(e -> navigationController.goBack()));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(UIFactory.createTitlePanel("Portfolio History"));
        contentPanel.add(createCenterPanel());
        this.add(contentPanel);

        wireListeners();
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        JScrollPane table = UIFactory.createStyledTable(tableModel);
        centerPanel.add(table, BorderLayout.CENTER);
        return centerPanel;
    }

    private void wireListeners() {
        this.historyViewModel.addPropertyChangeListener(evt -> {
            HistoryState state = this.historyViewModel.getState();

            String[] names = state.getTickers();
            int[] amounts = state.getAmounts();
            double[] prices = state.getPrices();
            LocalDate[] dates = state.getDates();
            tableModel.setRowCount(0);

            for (int i = 0; i < names.length; i++) {
                tableModel.addRow(new Object[] { names[i], amounts[i], String.format("$%.2f", prices[i]), dates[i] });
            }
        });
    }
}