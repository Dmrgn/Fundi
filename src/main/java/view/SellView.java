package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interfaceadapter.navigation.NavigationController;
import interfaceadapter.sell.SellController;
import interfaceadapter.sell.SellState;
import interfaceadapter.sell.SellViewModel;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.PanelFactory;
import view.ui.UiConstants;

/**
 * The View for the Sell Use Case.
 */
public class SellView extends AbstractBaseView implements PropertyChangeListener {

    private final SellViewModel sellViewModel;
    private final SellController sellController;
    private final NavigationController navigationController;

    public SellView(SellViewModel sellViewModel, SellController sellController,
            NavigationController navigationController) {
        super("sell");
        this.sellViewModel = sellViewModel;
        this.sellController = sellController;
        this.navigationController = navigationController;
        this.sellViewModel.addPropertyChangeListener(this);

        // Header: back + title
        getHeader().add(createBackButtonPanel(evt -> this.navigationController.goBack()), BorderLayout.WEST);
        JPanel titlePanel = PanelFactory.createTitlePanel("Sell Stock");
        titlePanel.setOpaque(false);
        getHeader().add(titlePanel, BorderLayout.CENTER);

        JTextField tickerField = FieldFactory.createTextField();
        JPanel tickerPanel = PanelFactory.createFormPanel("Ticker", tickerField);

        JTextField amountField = FieldFactory.createTextField();
        JPanel amountPanel = PanelFactory.createFormPanel("Amount", amountField);

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
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(new Color(230, 230, 255)); // Light color for contrast
        formPanel.add(UiConstants.smallVerticalGap());
        formPanel.add(balanceLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        final JButton sell = ButtonFactory.createStyledButton("Sell");
        sell.addActionListener(
                evt -> {
                    if (evt.getSource().equals(sell)) {
                        final SellState currentState = sellViewModel.getState();
                        this.sellController.execute(
                                currentState.getPortfolioId(),
                                tickerField.getText(),
                                Integer.parseInt(amountField.getText()));
                    }
                });
        mainPanel.add(ButtonFactory.createButtonPanel(sell), BorderLayout.SOUTH);
        getContent().add(mainPanel, BorderLayout.CENTER);

        this.sellViewModel.addPropertyChangeListener(evt -> {
            SellState s = this.sellViewModel.getState();
            balanceLabel.setText(String.format("Balance: $%.2f", s.getBalance()));
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SellState state = (SellState) evt.getNewValue();
        if (state.getSellError() != null) {
            JOptionPane.showMessageDialog(this, state.getSellError());
            state.setSellError(null);
        }
    }
}
