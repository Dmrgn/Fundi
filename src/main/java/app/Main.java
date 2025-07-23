package app; // START USING BRANCHES.........!!!!!!!!!!!!

import javax.swing.JFrame;
import java.sql.SQLException;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) throws SQLException {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addMainView()
                .addPortfoliosView()
                .addCreateView()
                .addPortfolioView()
                .addBuyView()
                .addSellView()
                .addLoginUseCase()
                .addSignupUseCase()
                .addPortfoliosUseCase()
                .addCreateUseCase()
                .addPortfolioUseCase()
                .addBuyUseCase()
                .addSellUseCase()
                .build();
        application.pack();
        application.setVisible(true);
    }
}