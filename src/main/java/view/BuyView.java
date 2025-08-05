package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyState;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.navigation.NavigationController;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import view.ui.PanelFactory;
import view.ui.UiConstants;

/**
 * The View for the Buy Use Case
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

        JPanel contentPanel = createGradientContentPanel();

        // Add back button (top left) using w/ NavigationController
        contentPanel.add(createBackButtonPanel(e -> this.navigationController.goBack()), BorderLayout.NORTH);

        JPanel welcomePanel = PanelFactory.createTitlePanel("Buy Stock");
        contentPanel.add(welcomePanel, BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setOpaque(false);

        JTextField tickerField = FieldFactory.createTextField();
        final JPanel tickerPanel = PanelFactory.createFormPanel("Ticker Name", tickerField);

        JTextField amountField = FieldFactory.createTextField();
        final JPanel amountPanel = PanelFactory.createFormPanel("Amount Name", amountField);

        formPanel.add(tickerPanel);
        formPanel.add(UiConstants.smallVerticalGap());
        formPanel.add(amountPanel);
        contentPanel.add(formPanel, BorderLayout.CENTER);

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
        contentPanel.add(ButtonFactory.createButtonPanel(buy), BorderLayout.SOUTH);
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