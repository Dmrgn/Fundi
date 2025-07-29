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
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;
import view.components.UIFactory;

/**
 * The View for when the user is trying to create a portfolio.
 */
public class BuyView extends BaseView implements PropertyChangeListener {

    private final BuyViewModel buyViewModel;
    private final BuyController buyController;

    public BuyView(BuyViewModel buyViewModel, BuyController buyController) {
        super("buy");
        this.buyViewModel = buyViewModel;
        this.buyController = buyController;
        this.buyViewModel.addPropertyChangeListener(this);

        JPanel contentPanel = createGradientContentPanel();

        JPanel welcomePanel = UIFactory.createTitlePanel("Buy Stock");
        contentPanel.add(welcomePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setOpaque(false);

        JTextField tickerField = UIFactory.createTextField();
        final JPanel tickerPanel = UIFactory.createFormPanel("Ticker Name", tickerField);

        JTextField amountField = UIFactory.createTextField();
        final JPanel amountPanel = UIFactory.createFormPanel("Amount Name", amountField);

        formPanel.add(tickerPanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(amountPanel);
        contentPanel.add(formPanel, BorderLayout.CENTER);

        final JButton buy = UIFactory.createStyledButton("Buy");
        buy.addActionListener(
                evt -> {
                    if (evt.getSource().equals(buy)) {
                        final BuyState currentState = buyViewModel.getState();
                        this.buyController.execute(
                                currentState.getPortfolioId(),
                                tickerField.getText(),
                                Integer.parseInt(amountField.getText())
                        );
                    }
                }
        );
        contentPanel.add(UIFactory.createButtonPanel(buy), BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);
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