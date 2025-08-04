package app;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * The Main class of our application.
 */
public class Main {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 900;

    /**
     * Our runnable Clean Architecture App.
     * @param args Unused args
     * @throws SQLException If the DAO queries fail
     * @throws IOException If the API calls fail
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
        application.setSize(WIDTH, HEIGHT);
        application.setVisible(true);
    }
}
