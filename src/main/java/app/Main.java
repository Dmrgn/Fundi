package app;

import javax.swing.JFrame;

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
    public static void main(String[] args) {

        // Set the look and feel for Java Swing components
        FlatLightLaf.setup();

        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addMainView()
                .addLoginView()
                .addSignupView()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
