package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.create.CreateController;
import interface_adapter.create.CreateState;
import interface_adapter.create.CreateViewModel;
import interface_adapter.ViewManagerModel;
import view.ui.*;

/**
 * The View For the Create Use Case
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

        // === 1. Top panel with plain text intro ===
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = LabelFactory.createTitleLabel("Create Portfolio");

        centerPanel.add(welcomeLabel);
        centerPanel.add(UiConstants.smallVerticalGap());

        JTextField createNameField = FieldFactory.createTextField();
        final JPanel form = PanelFactory.createFormPanel("Name: ", createNameField);
        centerPanel.add(form);
        final JButton create = ButtonFactory.createStyledButton("create");
        create.addActionListener(
                evt -> {
                    if (evt.getSource().equals(create)) {
                        final CreateState currentState = createViewModel.getState();
                        this.createController.execute(currentState.getUsername(), createNameField.getText());
                    }
                });
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
            state.setCreateError(null);
        }
    }
}
