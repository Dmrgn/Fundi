package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.navigation.NavigationController;
import interface_adapter.sell.SellController;
import interface_adapter.sell.SellState;
import interface_adapter.sell.SellViewModel;
import view.components.UiFactory;

/**
 * The View for the Sell Use Case
 */
public class SellView extends BaseView implements PropertyChangeListener {

    private final SellViewModel sellViewModel;
    private final SellController sellController;
    private final NavigationController navigationController;

    public SellView(SellViewModel sellViewModel, SellController sellController, NavigationController navigationController) {
        super("sell");
        this.sellViewModel = sellViewModel;
        this.sellController = sellController;
        this.navigationController = navigationController;
        this.sellViewModel.addPropertyChangeListener(this);

        JPanel contentPanel = createGradientContentPanel();


        // === 1. Top panel with plain text intro ===

        JPanel titlePanel = UiFactory.createTitlePanel("Sell Stock");
        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(createBackButtonPanel(e -> this.navigationController.goBack()), BorderLayout.NORTH);

        JTextField tickerField = UiFactory.createTextField();
        JPanel tickerPanel = UiFactory.createFormPanel("Ticker", tickerField);

        JTextField amountField = UiFactory.createTextField();
        JPanel amountPanel = UiFactory.createFormPanel("Amount", amountField);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setOpaque(false);
        formPanel.add(tickerPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(amountPanel);
        contentPanel.add(formPanel, BorderLayout.CENTER);


        final JButton buy = UiFactory.createStyledButton("Sell");
        buy.addActionListener(
                evt -> {
                    if (evt.getSource().equals(buy)) {
                        final SellState currentState = sellViewModel.getState();
                        this.sellController.execute(
                                currentState.getPortfolioId(),
                                tickerField.getText(),
                                Integer.parseInt(amountField.getText())
                        );
                    }
                }
        );
        contentPanel.add(UiFactory.createButtonPanel(buy), BorderLayout.SOUTH);
        this.add(contentPanel, BorderLayout.CENTER);
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