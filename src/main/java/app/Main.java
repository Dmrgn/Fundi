package app; // START USING BRANCHES.........!!!!!!!!!!!!

import javax.swing.JFrame;

import java.io.IOException;
import java.sql.SQLException;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * 
     * @param args unused arguments
     */

    public static void main(String[] args) throws SQLException, IOException {
        FlatLightLaf.setup();
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addPortfoliosView()
                .addCreateView()
                .addPortfolioView()
                .addBuyView()
                .addSellView()
                .addHistoryView()
                .addAnalysisView()
                .addRecommendView()
                .addNewsView()
                .addTabbedMainView()
                .addSettingsView()
                .build();
        application.pack();
        application.setSize(900, 600);
        // application.setExtendedState(JFrame.MAXIMIZED_BOTH);
        application.setVisible(true);
    }
}
