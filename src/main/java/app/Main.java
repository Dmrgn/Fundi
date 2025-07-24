package app; // START USING BRANCHES.........!!!!!!!!!!!!

import javax.swing.JFrame;
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

    public static void main(String[] args) throws SQLException {
        FlatLightLaf.setup();
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addMainView()
                .addPortfoliosView()
                .addCreateView()
                .addPortfolioView()
                .addLoginUseCase()
                .addSignupUseCase()
                .addPortfoliosUseCase()
                .addCreateUseCase()
                .addPortfolioUseCase()
                .build();
        application.pack();
        application.setSize(600, 400);
        // application.setExtendedState(JFrame.MAXIMIZED_BOTH);
        application.setVisible(true);
    }
}
