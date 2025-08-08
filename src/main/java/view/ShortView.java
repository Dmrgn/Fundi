package view;

import interface_adapter.navigation.NavigationController;
import interface_adapter.shortsell.ShortController;
import interface_adapter.shortsell.ShortState;
import interface_adapter.shortsell.ShortViewModel;
import view.ui.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ShortView extends BaseView implements PropertyChangeListener {
    private final ShortViewModel shortViewModel;
    private final ShortController shortController;
    private final NavigationController navigationController; // used for back navigation
    private JLabel errorLabel;

    public ShortView(ShortViewModel vm, ShortController controller, NavigationController navigationController) {
        super("short");
        this.shortViewModel = vm;
        this.shortController = controller;
        this.navigationController = navigationController;
        this.shortViewModel.addPropertyChangeListener(this);

        JPanel contentPanel = createGradientContentPanel();
        this.add(contentPanel, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    contentPanel.add(createBackButtonPanel(evt -> this.navigationController.goBack()), BorderLayout.NORTH);
        mainPanel.add(PanelFactory.createTitlePanel("Short Sell Stock"));
        mainPanel.add(UiConstants.mediumVerticalGap());

        JTextField tickerField = FieldFactory.createTextField();
        JPanel tickerPanel = PanelFactory.createFormPanel("Ticker", tickerField);

        JTextField amountField = FieldFactory.createTextField();
        JPanel amountPanel = PanelFactory.createFormPanel("Amount", amountField);

        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setMaximumSize(UiConstants.BUTTON_PANEL_DIM);
        formPanel.add(tickerPanel);
        formPanel.add(UiConstants.smallVerticalGap());
        formPanel.add(amountPanel);
        mainPanel.add(formPanel);

    errorLabel = LabelFactory.createLabel(""); // reuse regular label style for errors
    errorLabel.setForeground(Color.RED);
        mainPanel.add(UiConstants.smallVerticalGap());
        mainPanel.add(errorLabel);

        JButton shortBtn = ButtonFactory.createStyledButton("Short");
        shortBtn.addActionListener(e -> {
            ShortState s = shortViewModel.getState();
            try {
                int amt = Integer.parseInt(amountField.getText().trim());
                shortController.execute(s.getPortfolioId(), tickerField.getText().trim().toUpperCase(), amt);
            } catch (NumberFormatException ex) {
                errorLabel.setText("Invalid amount");
            }
        });
        mainPanel.add(UiConstants.mediumVerticalGap());
        mainPanel.add(ButtonFactory.createButtonPanel(shortBtn));
        mainPanel.add(Box.createVerticalGlue());

        contentPanel.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ShortState s = shortViewModel.getState();
        if (s.getError() != null) {
            errorLabel.setText(s.getError());
        } else {
            errorLabel.setText("");
        }
    }
}
