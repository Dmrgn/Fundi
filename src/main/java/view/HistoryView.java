package view;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//import interface_adapter.change_password.ChangePasswordController;
//import interface_adapter.change_password.LoggedInState;
//import interface_adapter.change_password.LoggedInViewModel;
//import interface_adapter.logout.LogoutController;
import entity.CurrencyConverter;
import interface_adapter.history.HistoryState;
import interface_adapter.history.HistoryViewModel;
import interface_adapter.navigation.NavigationController;
import view.ui.PanelFactory;
import view.ui.TableFactory;
import view.ui.UiConstants;

import static entity.PreferredCurrencyManager.getConverter;
import static entity.PreferredCurrencyManager.getPreferredCurrency;

/**
 * The View for the History Use Case
 */
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

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(createBackButtonPanel(evt -> this.navigationController.goBack()));
        contentPanel.add(UiConstants.mediumVerticalGap());
        contentPanel.add(PanelFactory.createTitlePanel("Portfolio History"));
        contentPanel.add(createCenterPanel());
        this.add(contentPanel);

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

            CurrencyConverter converter = getConverter();
            String preferredCurrency = getPreferredCurrency();

            for (int i = 0; i < names.length; i++) {

                double originalPrice = prices[i];
                double convertedPrice = originalPrice;

                if (converter != null && !preferredCurrency.equals("USD")) {
                    try {
                        convertedPrice = converter.convert(originalPrice, "USD", preferredCurrency);
                    } catch (Exception e) {
                        System.err.println("Currency conversion failed: " + e.getMessage());
                    }

                }
                tableModel.addRow(new Object[] {names[i], amounts[i], String.format("%.2f %s", convertedPrice, preferredCurrency), dates[i]});
            }
        });
    }
}