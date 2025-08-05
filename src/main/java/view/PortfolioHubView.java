package view;

import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio_hub.PortfolioHubState;
import interface_adapter.portfolio_hub.PortfolioHubViewModel;
import interface_adapter.portfolio_hub.PortfolioHubController;
import interface_adapter.navigation.NavigationController;
import view.components.ButtonFactory;
import view.components.LabelFactory;
import view.components.PanelFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * The View for the Portfolio Hub Use Case
 */
public class PortfolioHubView extends BaseView {
    private final PortfolioHubViewModel portfoliosViewModel;
    private final PortfolioHubController portfolioHubController;
    private final PortfolioController portfolioController;
    private final NavigationController navigationController;

    private final JPanel buttonPanel = ButtonFactory.createButtonPanel();

    public PortfolioHubView(PortfolioHubViewModel portfoliosViewModel, PortfolioHubController portfolioHubController,
                            PortfolioController portfolioController, NavigationController navigationController) {
        super("portfolio hub");
        this.portfoliosViewModel = portfoliosViewModel;
        this.portfolioHubController = portfolioHubController;
        this.portfolioController = portfolioController;
        this.navigationController = navigationController;

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(PanelFactory.createTitlePanel("Portfolios"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createCreateButtonPanel());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(LabelFactory.createLabel("Your Portfolios:"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        registerViewModelListener();
    }

    private JPanel createCreateButtonPanel() {
        JButton createButton = ButtonFactory.createStyledButton("Create New Portfolio");
        createButton.setAlignmentX((Component.CENTER_ALIGNMENT));
        createButton.addActionListener(e -> {
            PortfolioHubState portfoliosState = this.portfoliosViewModel.getState();
            this.portfolioHubController.routeToCreate(portfoliosState.getUsername());
        });

        return ButtonFactory.createButtonPanel(createButton);
    }

    private void registerViewModelListener() {
        portfoliosViewModel.addPropertyChangeListener(evt -> {
            buttonPanel.removeAll();
            PortfolioHubState portfoliosState = portfoliosViewModel.getState();
            Map<String, String> portfolios = portfoliosState.getPortfolios();

            for (String portfolio : portfolios.keySet()) {
                JButton button = ButtonFactory.createStyledButton(portfolio);
                button.addActionListener(e -> portfolioController.execute(portfoliosState.getUsername(),
                        portfolios.get(portfolio), portfolio));

                buttonPanel.add(button);
            }

            buttonPanel.revalidate();
            buttonPanel.repaint();
        });
    }
}
