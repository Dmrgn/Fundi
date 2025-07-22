package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.create.CreateController;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolios.PortfoliosController;
import interface_adapter.portfolios.PortfoliosState;
import interface_adapter.portfolios.PortfoliosViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class PortfoliosView extends JPanel {

    private final String viewName = "portfolios";
    private final PortfoliosViewModel portfoliosViewModel;
    private PortfoliosController portfoliosController;
    private PortfolioController portfolioController;

    public PortfoliosView(PortfoliosViewModel portfoliosViewModel) {
        this.portfoliosViewModel = portfoliosViewModel;
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === 1. Top panel with plain text intro ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Portfolios");
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton newPortfolio = new JButton("Create New Portfolio");
        newPortfolio.addActionListener(evt -> {
            final PortfoliosState currentState = portfoliosViewModel.getState();
            portfoliosController.routeToCreate(
                    currentState.getUsername()
            );
        });
        newPortfolio.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(welcomeLabel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(newPortfolio);
        topPanel.add(Box.createVerticalStrut(10));

        // === 3. Buttons ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout(10, 10));
        JLabel promptLabel = new JLabel("Your Portfolios:");
        promptLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setMaximumSize(new Dimension(400, 100));

        portfoliosViewModel.addPropertyChangeListener(evt -> {
            buttonPanel.removeAll();
            PortfoliosState state = portfoliosViewModel.getState();
            Map<String, String> portfolios = state.getPortfolios();

            portfolios.keySet().forEach(portfolio -> {
                JButton portfolioButton = new JButton(portfolio);
                portfolioButton.addActionListener(portEvt -> {
                    PortfoliosState portfoliosState = portfoliosViewModel.getState();
                    portfolioController.execute(portfoliosState.getUsername(), portfolios.get(portfolio), portfolio);

                });
                buttonPanel.add(portfolioButton);
            });
        });

        topPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(promptLabel, BorderLayout.NORTH);
        topPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add to main layout
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }

    public void setPortfoliosController(PortfoliosController portfoliosController) {
        this.portfoliosController = portfoliosController;
    }

    public void setPortfolioController(PortfolioController portfolioController) {
        this.portfolioController = portfolioController;
    }
}