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
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setOpaque(false);
        
        // Create a container for the back button with hover effect
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BorderLayout());
        buttonContainer.setOpaque(false);
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Style the back button
        backButton.setText("← Back");
        backButton.setFont(new Font("Sans Serif", Font.BOLD, 14));
        backButton.setForeground(new Color(200, 200, 200));
        backButton.setBackground(new Color(30, 60, 120));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 70, 130), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(40, 70, 130));
                backButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(30, 60, 120));
                backButton.setForeground(new Color(200, 200, 200));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(25, 50, 100));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(40, 70, 130));
            }
        });

        // Remove any existing listeners and add the new one
        for (java.awt.event.ActionListener l : backButton.getActionListeners()) {
            backButton.removeActionListener(l);
        }
        backButton.addActionListener(action);

        buttonContainer.add(backButton, BorderLayout.CENTER);
        panel.add(buttonContainer);

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
