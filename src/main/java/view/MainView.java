package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.main.MainController;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;

/**
 * The View for the Main Use Case.
 */
public class MainView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "main";

    private final MainViewModel mainViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private MainController mainController;

    private final JButton checkButton;

    public MainView(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        mainViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(MainViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(MainViewModel.USERNAME_LABEL), usernameInputField);

        final JPanel buttons = new JPanel();
        checkButton = new JButton(MainViewModel.TO_LOGIN_BUTTON_LABEL);
        buttons.add(checkButton);

        checkButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        mainController.switchToLoginView();
                    }
                });

        addUsernameListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(buttons);
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final MainState currentState = mainViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                mainViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final MainState state = (MainState) evt.getNewValue();
    }

    public String getViewName() {
        return viewName;
    }

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }
}
