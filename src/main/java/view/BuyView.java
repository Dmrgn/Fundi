package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interfaceadapter.buy.BuyController;
import interfaceadapter.buy.BuyState;
import interfaceadapter.buy.BuyViewModel;
import interfaceadapter.navigation.NavigationController;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.PanelFactory;
import view.ui.UiConstants;

/**
 * The View for the Buy Use Case.
 */
public class BuyView extends BaseView implements PropertyChangeListener {

    private final BuyViewModel buyViewModel;
    private final BuyController buyController;
    private final NavigationController navigationController;

    public BuyView(BuyViewModel buyViewModel, BuyController buyController, NavigationController navigationController) {
        super("buy");
        this.buyViewModel = buyViewModel;
        this.buyController = buyController;
        this.navigationController = navigationController;
        this.buyViewModel.addPropertyChangeListener(this);

        // Header back button and title
        header.add(createBackButtonPanel(evt -> this.navigationController.goBack()), BorderLayout.WEST);
        JPanel titlePanel = PanelFactory.createTitlePanel("Buy Stock");
        titlePanel.setOpaque(false);
        header.add(titlePanel, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        JTextField tickerField = FieldFactory.createTextField();
        final JPanel tickerPanel = PanelFactory.createFormPanel("Ticker Name: ", tickerField);

        JTextField amountField = FieldFactory.createTextField();
        final JPanel amountPanel = PanelFactory.createFormPanel("Amount: ", amountField);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setMaximumSize(UiConstants.BUTTON_PANEL_DIM);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setOpaque(false);

        formPanel.add(tickerPanel);
        formPanel.add(UiConstants.smallVerticalGap());
        formPanel.add(amountPanel);

            JLabel balanceLabel = new JLabel("Balance: $0.00");
            balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            balanceLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            balanceLabel.setForeground(new java.awt.Color(230, 230, 255)); // Light color for contrast
            formPanel.add(UiConstants.smallVerticalGap());
            formPanel.add(balanceLabel);

            this.buyViewModel.addPropertyChangeListener(evt -> {
                BuyState s = this.buyViewModel.getState();
                balanceLabel.setText(String.format("Balance: $%.2f", s.getBalance()));
            });

        mainPanel.add(formPanel, BorderLayout.CENTER);

        final JButton buy = ButtonFactory.createStyledButton("Buy");
        buy.addActionListener(
                evt -> {
                    if (evt.getSource().equals(buy)) {
                        final BuyState currentState = buyViewModel.getState();
                        this.buyController.execute(
                                currentState.getPortfolioId(),
                                tickerField.getText(),
                                Integer.parseInt(amountField.getText()));
                    }
                });
        mainPanel.add(ButtonFactory.createButtonPanel(buy), BorderLayout.SOUTH);
        content.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final BuyState state = (BuyState) evt.getNewValue();
        if (state.getBuyError() != null) {
            JOptionPane.showMessageDialog(this, state.getBuyError());
            state.setBuyError(null);
        }
    }
}
