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

import interface_adapter.create.CreateController;
import interface_adapter.create.CreateState;
import interface_adapter.create.CreateViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;
import view.components.UIFactory;

/**
 * The View for when the user is trying to create a portfolio.
 */
public class CreateView extends BaseView implements PropertyChangeListener {
    private final CreateViewModel createViewModel;
    private final CreateController createController;

    public CreateView(CreateViewModel createViewModel, CreateController createController) {
        super("create");
        this.createViewModel = createViewModel;
        this.createController = createController;
        this.createViewModel.addPropertyChangeListener(this);
        JPanel contentPanel = createGradientContentPanel();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(contentPanel, BorderLayout.CENTER);

        // === 1. Top panel with plain text intro ===
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = UIFactory.createTitleLabel("Create Portfolio");

        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(5));

        JTextField createNameField = UIFactory.createTextField();
        final JPanel form = UIFactory.createFormPanel("Name: ",createNameField);
        centerPanel.add(form);
        final JButton create = UIFactory.createStyledButton("create");
        create.addActionListener(
                evt -> {
                    if (evt.getSource().equals(create)) {
                        final CreateState currentState = createViewModel.getState();
                        this.createController.execute(currentState.getUsername(), createNameField.getText());
                    }
                }
        );
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(create);

        // Add to main layout
        contentPanel.add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreateState state = (CreateState) evt.getNewValue();
        if (state.getCreateError() != null) {
            JOptionPane.showMessageDialog(this, state.getCreateError());
        }
    }
}