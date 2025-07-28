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
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;

/**
 * The View for when the user is trying to create a portfolio.
 */
public class CreateView extends JPanel implements PropertyChangeListener {

    private final String viewName = "create";
    private final CreateViewModel createViewModel;
    private final CreateController createController;
    private final JLabel createError = new JLabel();

    public CreateView(CreateViewModel createViewModel, CreateController createController) {
        this.createViewModel = createViewModel;
        this.createController = createController;
        this.createViewModel.addPropertyChangeListener(this);
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === 1. Top panel with plain text intro ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Create Portfolio");
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(5));

        JTextField createNameField = new JTextField(20);
        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Name"), createNameField);
        usernameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(usernameInfo);
        createError.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(createError);
        final JButton create = new JButton("create");
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
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CreateState state = (CreateState) evt.getNewValue();
        createError.setText(state.getCreateError());
    }
}