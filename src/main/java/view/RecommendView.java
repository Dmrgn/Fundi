package view;

import java.awt.*;

import javax.swing.*;

//import interface_adapter.change_password.ChangePasswordController;
//import interface_adapter.change_password.LoggedInState;
//import interface_adapter.change_password.LoggedInViewModel;
//import interface_adapter.logout.LogoutController;
import interface_adapter.analysis.AnalysisController;
import interface_adapter.analysis.AnalysisInteractor;
import interface_adapter.analysis.AnalysisState;
import interface_adapter.analysis.AnalysisViewModel;
import interface_adapter.history.HistoryState;
import interface_adapter.main.MainState;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolios.PortfoliosController;
import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendState;
import interface_adapter.recommend.RecommendViewModel;

/**
 * The View for when the portfolio analysis in the program.
 */
public class RecommendView extends JPanel {

    private final String viewName = "recommend";
    private final RecommendViewModel recommendViewModel;
    private RecommendController recommendController;

    public RecommendView(RecommendViewModel recommendViewModel, RecommendController recommendController) {
        this.recommendViewModel = recommendViewModel;
        this.recommendController = recommendController;
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // === 1. Top panel with plain text intro ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Recommendations:");
        welcomeLabel.setFont(new Font("Sans Serif", Font.BOLD, 28));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(welcomeLabel);

        JPanel recPanel = new JPanel();
        recPanel.setLayout(new BoxLayout(recPanel, BoxLayout.Y_AXIS));
        recommendViewModel.addPropertyChangeListener(event -> {
            RecommendState recommendState = recommendViewModel.getState();
            String[] recommendations = recommendState.getRecommendations();
            double[] prices = recommendState.getPrices();
            for (int i = 0; i < recommendations.length; i++) {
                JLabel label = new JLabel((i + 1) + ". " + recommendations[i] + " (" + prices[i] + ")");
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                recPanel.add(label);
            }
        });

        JPanel bottomPanel = new JPanel();
        JButton useCaseButton = new JButton("back");
        useCaseButton.addActionListener(evt -> this.recommendController.routeToPortfolio());
        bottomPanel.add(useCaseButton, BorderLayout.CENTER);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(recPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return viewName;
    }
}