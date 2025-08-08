package view;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import interface_adapter.history.HistoryState;
import interface_adapter.history.HistoryViewModel;
import interface_adapter.navigation.NavigationController;
import view.ui.PanelFactory;
import view.ui.TableFactory;

public class HistoryView extends BaseView {
    private static final String[] COLUMN_NAMES = {"Ticker", "Quantity", "Price", "Date"};
    private final HistoryViewModel historyViewModel;
    private final interface_adapter.navigation.NavigationController navigationController;
    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public HistoryView(HistoryViewModel historyViewModel,
                       NavigationController navigationController) {
        super("history");
        this.historyViewModel = historyViewModel;
        this.navigationController = navigationController;

        header.add(createBackButtonPanel(evt -> this.navigationController.goBack()), BorderLayout.WEST);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(PanelFactory.createTitlePanel("Portfolio History"));
        contentPanel.add(createCenterPanel());

        content.add(contentPanel, BorderLayout.CENTER);

        wireListeners();
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        JScrollPane table = TableFactory.createStyledTable(tableModel);
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
                tableModel.addRow(new Object[] {names[i], amounts[i], String.format("$%.2f", prices[i]), dates[i] });
            }
        });
    }
}