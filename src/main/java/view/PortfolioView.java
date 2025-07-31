package view;

import interface_adapter.analysis.AnalysisController;
import interface_adapter.history.HistoryController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.recommend.RecommendController;
import interface_adapter.ViewManagerModel;
import view.components.UIFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PortfolioView extends BaseView {
    private final PortfolioViewModel portfolioViewModel;
    private final PortfolioController portfolioController;
    private final HistoryController historyController;
    private final AnalysisController analysisController;
    private final RecommendController recommendController;
    private final BackNavigationHelper backNavigationHelper;
    private final JLabel titleLabel = UIFactory.createTitleLabel("");
    private final JLabel usernameLabel = UIFactory.createLabel("");
    private static final String[] columnNames = { "Ticker", "Quantity", "Amount" };
    private static final String[] useCases = new String[] { "Analysis", "Recommendations", "History", "Buy", "Sell" };
    private final JButton backButton = UIFactory.createStyledButton("Back");
    private final JButton[] useCaseButtons = new JButton[useCases.length];
    private final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
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
        topPanel.add(createBackButtonPanel(e -> backNavigationHelper.goBackToPortfolios()));
        topPanel.setOpaque(false);
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(usernameLabel);
        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        JScrollPane table = UIFactory.createStyledTable(tableModel);
        centerPanel.add(table, BorderLayout.CENTER);
        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JPanel buttonPanel = UIFactory.createButtonPanel(useCaseButtons);
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        for (int i = 0; i < useCaseButtons.length; i++) {
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            buttonPanel.add(useCaseButtons[i], gbc);
        }
        buttonPanel.setMaximumSize(new Dimension(600, 100));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(buttonPanel);
        bottomPanel.add(Box.createVerticalStrut(20));
        // bottomPanel.add(createBackButtonPanel(e -> navigationController.goBack()));
        return bottomPanel;
    }

    private void generateButtons() {
        for (int i = 0; i < useCases.length; i++) {
            this.useCaseButtons[i] = UIFactory.createStyledButton(useCases[i]);
            this.useCaseButtons[i].setPreferredSize(new Dimension(180, 30));
            this.useCaseButtons[i].setMaximumSize(new Dimension(180, 30));
            this.useCaseButtons[i].setMinimumSize(new Dimension(180, 30));
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

            for (int i = 0; i < names.length; i++) {
                tableModel.addRow(new Object[] { names[i], amounts[i], String.format("$%.2f", prices[i]) });
            }
        });

        for (int i = 0; i < useCases.length; i++) {
            PortfolioState state = this.portfolioViewModel.getState();
            JButton useCaseButton = useCaseButtons[i];
            useCaseButton.addActionListener(e -> {
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
                }
            });
        }
    }
}
