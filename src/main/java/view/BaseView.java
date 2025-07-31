package view;

import javax.swing.*;
import java.awt.*;

/**
 * The generic base class for all of the views in our app
 */
public abstract class BaseView extends JPanel {
    protected String viewName;
    protected final JButton backButton = new JButton("Back");

    protected BaseView(String viewName) {
        this.viewName = viewName;
        setPreferredSize(new Dimension(1500, 1000));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setOpaque(false);
    }

    /**
     * Call in subclass for a back button.
     * 
     * @param action the ActionListener for the back button
     * @return the panel containing the back button
     */
    protected JPanel createBackButtonPanel(java.awt.event.ActionListener action) {
        for (java.awt.event.ActionListener l : backButton.getActionListeners()) {
            backButton.removeActionListener(l);
        }
        backButton.addActionListener(action);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);
        panel.add(backButton);
        return panel;
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Build the generic panel for the app
     * 
     * @return the coloured panel
     */
    protected JPanel createGradientContentPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color c1 = new Color(10, 30, 60);
                Color c2 = new Color(30, 60, 120);
                g2d.setPaint(new GradientPaint(0, 0, c1, width, height, c2));
                g2d.fillRect(0, 0, width, height);
            }
        };
        panel.setLayout(new BorderLayout(10, 10));
        panel.setOpaque(false);
        return panel;
    }

}
