package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyState;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.create.CreateController;
import interface_adapter.create.CreateState;
import interface_adapter.create.CreateViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;
import interface_adapter.sell.SellController;
import interface_adapter.sell.SellPresenter;
import interface_adapter.sell.SellState;
import interface_adapter.sell.SellViewModel;
import view.components.UIFactory;
import interface_adapter.navigation.NavigationController;

/**
 * The View for when the user is trying to create a portfolio.
 */
public class SellView extends BaseView implements PropertyChangeListener {

    private final SellViewModel sellViewModel;
    private final JLabel sellError = new JLabel();
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

        JPanel titlePanel = UIFactory.createTitlePanel("Sell Stock");
        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(createBackButtonPanel(e -> navigationController.goBack()), BorderLayout.NORTH);

        JTextField tickerField = UIFactory.createTextField();
        JPanel tickerPanel = UIFactory.createFormPanel("Ticker", tickerField);

        JTextField amountField = UIFactory.createTextField();
        JPanel amountPanel = UIFactory.createFormPanel("Amount", amountField);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setOpaque(false);
        formPanel.add(tickerPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(amountPanel);
        contentPanel.add(formPanel, BorderLayout.CENTER);


        final JButton buy = UIFactory.createStyledButton("Sell");
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
        contentPanel.add(UIFactory.createButtonPanel(buy), BorderLayout.SOUTH);
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