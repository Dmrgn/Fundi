package view;

import java.awt.*;

import javax.swing.*;

//import interface_adapter.change_password.ChangePasswordController;
//import interface_adapter.change_password.LoggedInState;
//import interface_adapter.change_password.LoggedInViewModel;
//import interface_adapter.logout.LogoutController;
import interface_adapter.analysis.AnalysisController;
import interface_adapter.analysis.AnalysisState;
import interface_adapter.analysis.AnalysisViewModel;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolios.PortfoliosController;

/**
 * The View for when the portfolio analysis in the program.
 */
public class AnalysisView extends JPanel {

    private final String viewName = "analysis";
    private final AnalysisViewModel analysisViewModel;

    public AnalysisView(AnalysisViewModel analysisViewModel) {
        this.analysisViewModel = analysisViewModel;
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === 1. Top panel with plain text intro ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Portfolio Analysis");
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(welcomeLabel);

        JPanel spreadPanel = new JPanel();
        spreadPanel.setLayout(new BoxLayout(spreadPanel, BoxLayout.Y_AXIS));
        JLabel numTickersLabel = new JLabel();

        analysisViewModel.addPropertyChangeListener(event -> {
            AnalysisState analysisState = analysisViewModel.getState();
            numTickersLabel.setText("Number of Tickers : " + analysisState.getNumTickers());
        });

        JPanel topSpreadPanel = new JPanel();
        topSpreadPanel.add(new JLabel("Highest Holdings:" ));
        topSpreadPanel.setLayout(new BoxLayout(topSpreadPanel, BoxLayout.Y_AXIS));
        analysisViewModel.addPropertyChangeListener(event -> {
            AnalysisState analysisState = analysisViewModel.getState();
            int i = 1;
            for (String ticker : analysisState.getMajorityTickers().keySet()) {
                topSpreadPanel.add(new JLabel(i  + ". " + ticker + ": " + analysisState.getMajorityTickers().get(ticker) + "%"));
                i++;
            }
        });
        spreadPanel.add(numTickersLabel);
        spreadPanel.add(topSpreadPanel);

        JPanel volPanel = new JPanel();
        volPanel.setLayout(new BoxLayout(volPanel, BoxLayout.Y_AXIS));
        JLabel volLabel = new JLabel();

        analysisViewModel.addPropertyChangeListener(event -> {
            AnalysisState analysisState = analysisViewModel.getState();
            volLabel.setText("Total Volatility: " + analysisState.getVolatility() + "%");
        });

        JPanel topVolPanel = new JPanel();
        topVolPanel.add(new JLabel("Highest Volatility:"));
        topVolPanel.setLayout(new BoxLayout(topVolPanel, BoxLayout.Y_AXIS));
        analysisViewModel.addPropertyChangeListener(event -> {
            AnalysisState analysisState = analysisViewModel.getState();
            int i = 1;
            for (String ticker : analysisState.getMostVolTickers().keySet()) {
                topVolPanel.add(new JLabel(i  + ". " + ticker + ": " + analysisState.getMostVolTickers().get(ticker) + "%"));
                i++;
            }
        });

        JPanel bottomVolPanel = new JPanel();
        bottomVolPanel.add(new JLabel("Lowest Volatility:"));
        bottomVolPanel.setLayout(new BoxLayout(bottomVolPanel, BoxLayout.Y_AXIS));
        analysisViewModel.addPropertyChangeListener(event -> {
            AnalysisState analysisState = analysisViewModel.getState();
            int i = 1;
            for (String ticker : analysisState.getLeastVolTickers().keySet()) {
                bottomVolPanel.add(new JLabel(i  + ". " + ticker + ": " + analysisState.getLeastVolTickers().get(ticker) + "%"));
                i++;
            }
        });

        volPanel.add(volLabel);
        volPanel.add(topVolPanel);
        volPanel.add(bottomVolPanel);

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));
        JLabel returnLabel = new JLabel();

        analysisViewModel.addPropertyChangeListener(event -> {
            AnalysisState analysisState = analysisViewModel.getState();
            returnLabel.setText("Total Return: " + analysisState.getPastReturn() + "%");
        });

        JPanel topReturn = new JPanel();
        topReturn.add(new JLabel("Highest Returns:"));
        topReturn.setLayout(new BoxLayout(topReturn, BoxLayout.Y_AXIS));
        analysisViewModel.addPropertyChangeListener(event -> {
            AnalysisState analysisState = analysisViewModel.getState();
            int i = 1;
            for (String ticker : analysisState.getTopReturns().keySet()) {
                topReturn.add(new JLabel(i  + ". " + ticker + ": " + analysisState.getTopReturns().get(ticker) + "%"));
                i++;
            }
        });

        JPanel bottomReturn = new JPanel();
        bottomReturn.add(new JLabel("Lowest Returns:"));
        bottomReturn.setLayout(new BoxLayout(bottomReturn, BoxLayout.Y_AXIS));
        analysisViewModel.addPropertyChangeListener(event -> {
            AnalysisState analysisState = analysisViewModel.getState();
            int i = 1;
            for (String ticker : analysisState.getWorstReturns().keySet()) {
                bottomReturn.add(new JLabel(i  + ". " + ticker + ": " + analysisState.getWorstReturns().get(ticker) + "%"));
                i++;
            }
        });

        returnPanel.add(returnLabel);
        returnPanel.add(topReturn);
        returnPanel.add(bottomReturn);

        this.add(topPanel, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(spreadPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(volPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(returnPanel);
        this.add(mainPanel, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }
}