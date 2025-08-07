package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.navigation.NavigationController;
import interface_adapter.sell.SellController;
import interface_adapter.sell.SellState;
import interface_adapter.sell.SellViewModel;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.PanelFactory;
import view.ui.UiConstants;

/**
 * The View for the Sell Use Case.
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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        // === 1. Top panel with plain text intro ===

        JPanel titlePanel = PanelFactory.createTitlePanel("Sell Stock");
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(createBackButtonPanel(evt -> {
            this.navigationController.goBack();
        }), BorderLayout.NORTH);

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
        mainPanel.add(formPanel, BorderLayout.CENTER);

        final JButton buy = ButtonFactory.createStyledButton("Sell");
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
        mainPanel.add(ButtonFactory.createButtonPanel(buy), BorderLayout.SOUTH);
        contentPanel.add(mainPanel, BorderLayout.CENTER);
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