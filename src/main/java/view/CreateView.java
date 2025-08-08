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

        // Header
        header.add(createBackButtonPanel(evt -> backNavigationHelper.goBackToPortfolios()), BorderLayout.WEST);
        JLabel title = LabelFactory.createTitleLabel("Create Portfolio");
        JPanel titleWrap = new JPanel();
        titleWrap.setOpaque(false);
        titleWrap.setLayout(new BoxLayout(titleWrap, BoxLayout.Y_AXIS));
        titleWrap.add(title);
        header.add(titleWrap, BorderLayout.CENTER);

        // Content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setMaximumSize(UiConstants.BUTTON_PANEL_DIM);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField createNameField = FieldFactory.createTextField();
        final JPanel form = PanelFactory.createFormPanel("Name: ", createNameField);
        centerPanel.add(form);
        final JButton create = ButtonFactory.createPrimaryButton("Create");
        create.addActionListener(
                evt -> {
                    if (evt.getSource().equals(create)) {
                        final CreateState currentState = createViewModel.getState();
                        this.createController.execute(currentState.getUsername(), createNameField.getText());
                    }
                });
        create.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createVerticalStrut(UiConstants.Spacing.MD));
        centerPanel.add(create);

        content.add(centerPanel, BorderLayout.NORTH);
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
