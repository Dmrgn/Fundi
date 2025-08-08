package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.ViewManagerModel;
import interface_adapter.create.CreateController;
import interface_adapter.create.CreateState;
import interface_adapter.create.CreateViewModel;
import view.ui.*;

/**
 * The View For the Create Use Case.
 */
public class CreateView extends BaseView implements PropertyChangeListener {
    private final CreateViewModel createViewModel;
    private final CreateController createController;
    private final BackNavigationHelper backNavigationHelper;

    public CreateView(CreateViewModel createViewModel, CreateController createController,
            ViewManagerModel viewManagerModel) {
        super("create");
        this.createViewModel = createViewModel;
        this.createController = createController;
        this.backNavigationHelper = new BackNavigationHelper(viewManagerModel);
        this.createViewModel.addPropertyChangeListener(this);
        JPanel contentPanel = createGradientContentPanel();
        setLayout(UiConstants.BORDER_LAYOUT);
        setBorder(UiConstants.EMPTY_BORDER);
        this.add(contentPanel, BorderLayout.CENTER);

        // Add back button
        contentPanel.add(createBackButtonPanel(evt -> {
            backNavigationHelper.goBackToPortfolios();
        }), BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        JPanel welcomePanel = PanelFactory.createTitlePanel("Create Portfolio");
        mainPanel.add(welcomePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setMaximumSize(UiConstants.BUTTON_PANEL_DIM);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setOpaque(false);

        JTextField createNameField = FieldFactory.createTextField();
        final JPanel form = PanelFactory.createFormPanel("Name: ", createNameField);
        centerPanel.add(form, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        final JButton create = ButtonFactory.createStyledButton("create");
        create.addActionListener(
                evt -> {
                    if (evt.getSource().equals(create)) {
                        final CreateState currentState = createViewModel.getState();
                        this.createController.execute(currentState.getUsername(), createNameField.getText());
                    }
                });
        mainPanel.add(ButtonFactory.createButtonPanel(create), BorderLayout.SOUTH);
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreateState state = (CreateState) evt.getNewValue();
        if (state.getCreateError() != null) {
            JOptionPane.showMessageDialog(this, state.getCreateError());
            state.setCreateError(null);
        }
    }
}
