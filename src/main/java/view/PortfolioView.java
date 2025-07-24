package view;

import java.awt.*;

import javax.swing.*;

//import interface_adapter.change_password.ChangePasswordController;
//import interface_adapter.change_password.LoggedInState;
//import interface_adapter.change_password.LoggedInViewModel;
//import interface_adapter.logout.LogoutController;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolio.PortfolioState;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.portfolios.PortfoliosController;

/**
 * The View for when the user is looking at a portfolio in the program.
 */
public class PortfolioView extends JPanel {

    private final String viewName = "portfolio";
    private final PortfolioViewModel portfolioViewModel;

    public PortfolioView(PortfolioViewModel portfolioViewModel) {
        this.portfolioViewModel = portfolioViewModel;
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === 1. Top panel with plain text intro ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel();
        portfolioViewModel.addPropertyChangeListener(evt -> {
            welcomeLabel.setText("Portfolio: " + portfolioViewModel.getState().getPortfolioName());
        });
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel usernameLabel = new JLabel();
        portfolioViewModel.addPropertyChangeListener(evt -> {
            PortfolioState state = portfolioViewModel.getState();
            usernameLabel.setText("Logged in as: " + state.getUsername());
        });
        usernameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(welcomeLabel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(usernameLabel);
        topPanel.add(Box.createVerticalStrut(10));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout(10, 10));

        portfolioViewModel.addPropertyChangeListener(evt -> {
            PortfolioState state = portfolioViewModel.getState();
            String[] names = state.getStockNames();
            int[] amounts = state.getStockAmounts();
            double[] prices = state.getStockPrices();
            Object[][] rowData = new Object[names.length][3];
            for (int i = 0; i < names.length; i++) {
                rowData[i][0] = names[i];
                rowData[i][1] = amounts[i];
                rowData[i][2] = prices[i];
            }
            String[] columnNames = {"Ticker", "Quantity", "Price"};
            JTable table = new JTable(rowData, columnNames);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(900, 600));
            centerPanel.add(scrollPane, BorderLayout.CENTER);
        });


        // === 3. Buttons ===
        JPanel bottomPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setMaximumSize(new Dimension(400, 100));

        String[] useCases = new String[] {"Analysis", "Recommendations", "History", "Buy", "Sell", "Delete"};

        for (String useCase : useCases) {
            JButton useCaseButton = new JButton(useCase);

            useCaseButton.addActionListener(evt -> {

            });
            buttonPanel.add(useCaseButton);
        }

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
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