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
import interface_adapter.sell.SellController;
import interface_adapter.sell.SellPresenter;
import interface_adapter.sell.SellState;
import interface_adapter.sell.SellViewModel;

/**
 * The View for when the user is trying to create a portfolio.
 */
public class SellView extends JPanel implements PropertyChangeListener {

    private final String viewName = "sell";
    private final SellViewModel sellViewModel;
    private final JLabel sellError = new JLabel();
    private SellController sellController;

    public SellView(SellViewModel sellViewModel) {
        this.sellViewModel = sellViewModel;
        this.sellViewModel.addPropertyChangeListener(this);
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === 1. Top panel with plain text intro ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Sell Stock");
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(5));

        JTextField tickerField = new JTextField(20);
        final LabelTextPanel tickerInfo = new LabelTextPanel(
                new JLabel("Ticker Name"), tickerField);
        tickerInfo.setAlignmentX(Component.CENTER_ALIGNMENT);


        JTextField amountField = new JTextField(20);
        final LabelTextPanel amountInfo = new LabelTextPanel(
                new JLabel("Amount"), amountField);
        amountInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        sellError.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(tickerInfo);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(amountInfo);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(sellError);
        add(centerPanel, BorderLayout.CENTER);

        final JButton buy = new JButton("Sell");
        buy.addActionListener(
                evt -> {
                    if (evt.getSource().equals(buy)) {
                        final SellState currentState = sellViewModel.getState();
                        sellController.execute(
                                currentState.getPortfolioId(),
                                tickerField.getText(),
                                Integer.parseInt(amountField.getText())
                        );
                    }
                }
        );
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(buy);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SellState state = (SellState) evt.getNewValue();
        sellError.setText("");
        sellError.setText(state.getSellError());
    }

    public void setSellController(SellController sellController) {
        this.sellController = sellController;
    }
}