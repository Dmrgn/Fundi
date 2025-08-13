package view;

import entity.CurrencyConverter;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.analysis.AnalysisController;
import interfaceadapter.history.HistoryController;
import interfaceadapter.portfolio.PortfolioController;
import interfaceadapter.portfolio.PortfolioState;
import interfaceadapter.portfolio.PortfolioViewModel;
import interfaceadapter.portfolio.DeletePortfolioController;
import interfaceadapter.recommend.RecommendController;
import view.ui.ButtonFactory;
import view.ui.LabelFactory;
import view.ui.TableFactory;
import view.ui.UiConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static entity.PreferredCurrencyManager.getConverter;
import static entity.PreferredCurrencyManager.getPreferredCurrency;

public class PortfolioView extends AbstractBaseView {
    private static final String[] COLUMN_NAMES = { "Ticker", "Quantity", "Amount" };
    private static final String[] USE_CASES = new String[] { "Analysis", "Recommendations", "History", "Buy", "Sell",
            "Delete" };
    private final PortfolioViewModel portfolioViewModel;
    private final PortfolioController portfolioController;
    private final HistoryController historyController;
    private final AnalysisController analysisController;
    private final RecommendController recommendController;
    private final DeletePortfolioController deletePortfolioController;
    private final BackNavigationHelper backNavigationHelper;
    private final JLabel titleLabel = LabelFactory.createTitleLabel("");
    private final JLabel usernameLabel = LabelFactory.createLabel("");
    private final JLabel balanceLabel = LabelFactory.createLabel("");
    private final JButton[] useCaseButtons = new JButton[USE_CASES.length];
    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public PortfolioView(PortfolioViewModel portfolioViewModel, PortfolioController portfolioController,
            HistoryController historyController, AnalysisController analysisController,
            RecommendController recommendController, ViewManagerModel viewManagerModel,
            DeletePortfolioController deletePortfolioController) {
        super("portfolio");
        this.portfolioViewModel = portfolioViewModel;
        this.portfolioController = portfolioController;
        this.historyController = historyController;
        this.analysisController = analysisController;
        this.recommendController = recommendController;
        this.deletePortfolioController = deletePortfolioController;
        this.backNavigationHelper = new BackNavigationHelper(viewManagerModel);

        generateButtons();

        // Header with back button and title
        JPanel headerTop = new JPanel();
        headerTop.setOpaque(false);
        headerTop.setLayout(new BoxLayout(headerTop, BoxLayout.Y_AXIS));
        headerTop.add(createBackButtonPanel(evt -> backNavigationHelper.goBackToPortfolios()));
        headerTop.add(titleLabel);
        headerTop.add(UiConstants.mediumVerticalGap());
        headerTop.add(usernameLabel);
        headerTop.add(UiConstants.mediumVerticalGap());
        headerTop.add(balanceLabel);
        getHeader().add(headerTop, BorderLayout.CENTER);

        // Content: table + actions
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        JScrollPane table = TableFactory.createStyledTable(tableModel);
        centerPanel.add(table, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JPanel buttonPanel = ButtonFactory.createButtonPanel(useCaseButtons);
        buttonPanel.setMaximumSize(UiConstants.BUTTON_PANEL_DIM);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(buttonPanel);
        bottomPanel.add(UiConstants.bigVerticalGap());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContent().add(contentPanel, BorderLayout.CENTER);

        wireListeners();
    }

    private void generateButtons() {
        for (int i = 0; i < USE_CASES.length; i++) {
            this.useCaseButtons[i] = ButtonFactory.createStyledButton(USE_CASES[i]);
            this.useCaseButtons[i].setPreferredSize(UiConstants.PREFERRED_COMPONENT_DIM);
            this.useCaseButtons[i].setMaximumSize(UiConstants.PREFERRED_COMPONENT_DIM);
            this.useCaseButtons[i].setMinimumSize(UiConstants.PREFERRED_COMPONENT_DIM);
        }
    }

    private void wireListeners() {
        this.portfolioViewModel.addPropertyChangeListener(evt -> {
            PortfolioState state = this.portfolioViewModel.getState();
            this.usernameLabel.setText("Logged in as: " + state.getUsername());
            this.titleLabel.setText("Portfolio: " + state.getPortfolioName());
            this.balanceLabel.setText(String.format("Balance: $%.2f", state.getBalance()));

            String[] names = state.getStockNames();
            int[] amounts = state.getStockAmounts();
            double[] prices = state.getStockPrices();
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

                tableModel.addRow(new Object[] {
                        names[i], amounts[i], String.format("%.2f %s", convertedPrice, preferredCurrency) });
            }
        });

        for (int i = 0; i < USE_CASES.length; i++) {
            PortfolioState state = this.portfolioViewModel.getState();
            JButton useCaseButton = useCaseButtons[i];
            useCaseButton.addActionListener(evt -> {
                if (useCaseButton.getText().equals("Buy")) {
                    this.portfolioController.routeToBuy(state.getPortfolioId());
                } else if (useCaseButton.getText().equals("Sell")) {
                    this.portfolioController.routeToSell(state.getPortfolioId());
                } else if (useCaseButton.getText().equals("History")) {
                    this.historyController.execute(state.getPortfolioId());
                } else if (useCaseButton.getText().equals("Analysis")) {
                    this.analysisController.execute(state.getPortfolioId());
                } else if (useCaseButton.getText().equals("Recommendations")) {
                    this.recommendController.execute(state.getPortfolioId());
                } else if (useCaseButton.getText().equals("Delete")) {
                    int res = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to delete this portfolio? This cannot be undone.",
                            "Delete Portfolio",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (res == JOptionPane.YES_OPTION) {
                        System.out.println("PortfolioView: Delete confirmed, calling controller with username="
                                + state.getUsername() + ", portfolioName=" + state.getPortfolioName());
                        deletePortfolioController.execute(state.getUsername(), state.getPortfolioName());
                        System.out.println("PortfolioView: Delete controller execute() completed");
                    }
                }
            });
        }
    }
}
