package view;

import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;
import interface_adapter.portfolios.PortfoliosController;
import interface_adapter.navigation.NavigationController;
import view.components.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PortfoliosView extends BaseView {
    private final PortfoliosViewModel portfoliosViewModel;
    private final PortfoliosController portfoliosController;
    private final PortfolioController portfolioController;
    private final NavigationController navigationController;

    private final JPanel buttonPanel = UIFactory.createButtonPanel();

    public PortfoliosView(PortfoliosViewModel portfoliosViewModel, PortfoliosController portfoliosController,
            PortfolioController portfolioController, NavigationController navigationController) {
        super("portfolios");
        this.portfoliosViewModel = portfoliosViewModel;
        this.portfoliosController = portfoliosController;
        this.portfolioController = portfolioController;
        this.navigationController = navigationController;

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(UIFactory.createTitlePanel("Portfolios"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createCreateButtonPanel());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(UIFactory.createLabel("Your Portfolios:"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        registerViewModelListener();
    }

    private JPanel createCreateButtonPanel() {
        JButton createButton = UIFactory.createStyledButton("Create New Portfolio");
        createButton.setAlignmentX((Component.CENTER_ALIGNMENT));
        createButton.addActionListener(e -> {
            PortfoliosState portfoliosState = portfoliosViewModel.getState();
            portfoliosController.routeToCreate(portfoliosState.getUsername());
        });

        return UIFactory.createButtonPanel(createButton);
    }

    private void registerViewModelListener() {
        portfoliosViewModel.addPropertyChangeListener(evt -> {
            buttonPanel.removeAll();
            PortfoliosState portfoliosState = portfoliosViewModel.getState();
            Map<String, String> portfolios = portfoliosState.getPortfolios();

            for (String portfolio : portfolios.keySet()) {
                JButton button = UIFactory.createStyledButton(portfolio);
                button.addActionListener(e -> portfolioController.execute(portfoliosState.getUsername(),
                        portfolios.get(portfolio), portfolio));

                buttonPanel.add(button);
            }

            buttonPanel.revalidate();
            buttonPanel.repaint();
        });
    }
}
