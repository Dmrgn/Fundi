package view;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entity.CurrencyConverter;
import interfaceadapter.history.HistoryState;
import interfaceadapter.history.HistoryViewModel;
import interfaceadapter.navigation.NavigationController;
import view.ui.PanelFactory;
import view.ui.TableFactory;

import static entity.PreferredCurrencyManager.getConverter;
import static entity.PreferredCurrencyManager.getPreferredCurrency;

public class HistoryView extends AbstractBaseView {
    private static final String[] COLUMN_NAMES = { "Ticker", "Quantity", "Price", "Date" };
    private final HistoryViewModel historyViewModel;
    private final interfaceadapter.navigation.NavigationController navigationController;
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

        getHeader().add(createBackButtonPanel(evt -> this.navigationController.goBack()), BorderLayout.WEST);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.add(PanelFactory.createTitlePanel("Portfolio History"));
        contentPanel.add(createCenterPanel());

        getContent().add(contentPanel, BorderLayout.CENTER);

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
                tableModel.addRow(new Object[] { names[i], amounts[i],
                        String.format("%.2f %s", convertedPrice, preferredCurrency), dates[i] });
            }
        });
    }
}
