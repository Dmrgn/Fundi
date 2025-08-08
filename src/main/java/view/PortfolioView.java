package view;

import entity.CurrencyConverter;
import interface_adapter.ViewManagerModel;
import interface_adapter.analysis.AnalysisController;
import interface_adapter.history.HistoryController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.recommend.RecommendController;
import view.ui.ButtonFactory;
import view.ui.LabelFactory;
import view.ui.TableFactory;
import view.ui.UiConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static entity.PreferredCurrencyManager.getConverter;
import static entity.PreferredCurrencyManager.getPreferredCurrency;

/**
 * The View for the Portfolio Use Case
 */
public class PortfolioView extends BaseView {
    private static final String[] COLUMN_NAMES = {"Ticker", "Quantity", "Amount" };
    private static final String[] USE_CASES = new String[] {"Analysis", "Recommendations", "History", "Buy", "Sell", "Short"};
    private final PortfolioViewModel portfolioViewModel;
    private final PortfolioController portfolioController;
    private final HistoryController historyController;
    private final AnalysisController analysisController;
    private final RecommendController recommendController;
    private final BackNavigationHelper backNavigationHelper;
    private final JLabel titleLabel = LabelFactory.createTitleLabel("");
    private final JLabel usernameLabel = LabelFactory.createLabel("");
    private final JButton backButton = ButtonFactory.createStyledButton("Back");
    private final JButton[] useCaseButtons = new JButton[USE_CASES.length];
    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public PortfolioView(PortfolioViewModel portfolioViewModel, PortfolioController portfolioController,
            HistoryController historyController, AnalysisController analysisController,
            RecommendController recommendController, ViewManagerModel viewManagerModel) {
        super("portfolio");
        this.portfolioViewModel = portfolioViewModel;
        this.portfolioController = portfolioController;
        this.historyController = historyController;
        this.analysisController = analysisController;
        this.recommendController = recommendController;
        this.backNavigationHelper = new BackNavigationHelper(viewManagerModel);

        generateButtons();

        JPanel contentPanel = createGradientContentPanel();
        this.add(contentPanel, BorderLayout.CENTER);

        JPanel topPanel = createTopPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel bottomPanel = createBottomPanel();

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        wireListeners();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(createBackButtonPanel(evt -> backNavigationHelper.goBackToPortfolios()));
        topPanel.setOpaque(false);
        topPanel.add(titleLabel);
        topPanel.add(UiConstants.mediumVerticalGap());
        topPanel.add(usernameLabel);
        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        JScrollPane table = TableFactory.createStyledTable(tableModel);
        centerPanel.add(table, BorderLayout.CENTER);
        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JPanel buttonPanel = ButtonFactory.createButtonPanel(useCaseButtons);
        buttonPanel.setMaximumSize(UiConstants.BUTTON_PANEL_DIM);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(buttonPanel);
        bottomPanel.add(UiConstants.bigVerticalGap());
        // bottomPanel.add(createBackButtonPanel(e -> navigationController.goBack()));
        return bottomPanel;
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
                    }
                    catch (Exception e) {
                        System.err.println("Currency conversion failed: " + e.getMessage());
                    }

                }

                tableModel.addRow(new Object[] {
                        names[i], amounts[i], String.format("%.2f %s", convertedPrice, preferredCurrency)});
            }
        });





        for (int i = 0; i < USE_CASES.length; i++) {
            PortfolioState state = this.portfolioViewModel.getState();
            JButton useCaseButton = useCaseButtons[i];
            useCaseButton.addActionListener(evt -> {
                if (useCaseButton.getText().equals("Buy")) {
                    this.portfolioController.routeToBuy(state.getPortfolioId());
                }

                else if (useCaseButton.getText().equals("Sell")) {
                    this.portfolioController.routeToSell(state.getPortfolioId());
                }

                else if (useCaseButton.getText().equals("History")) {
                    this.historyController.execute(state.getPortfolioId());
                }

                else if (useCaseButton.getText().equals("Analysis")) {
                    this.analysisController.execute(state.getPortfolioId());
                }

                else if (useCaseButton.getText().equals("Recommendations")) {
                    this.recommendController.execute(state.getPortfolioId());
                }
                else if (useCaseButton.getText().equals("Short")) {
                    this.portfolioController.routeToShort(state.getPortfolioId());
                }
            });
        }
    }
}
