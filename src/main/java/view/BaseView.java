package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import view.ui.DefaultTheme;
import view.ui.Theme;

/**
 * The generic base class for all the views in our app.
 */
public abstract class BaseView extends JPanel {
    private static final int BACKBTN_H_GAP = 15;
    private static final int BACKBTN_V_GAP = 10;
    private static final int BTN_CONTAINER_PADDING = 5;
    private static final int BTN_LINE_BORDER_WIDTH = 1;
    private static final int BTN_PAD_VERTICAL = 8;
    private static final int BTN_PAD_HORIZONTAL = 15;
    private static final int PANEL_GAP = 3;
    private static final int RGB = 200;
    protected String viewName;
    protected final JButton backButton = new JButton("Back");
    protected final Theme theme;

    // Standard areas
    protected final JPanel header = new JPanel(new BorderLayout());
    protected final JPanel content = new JPanel(new BorderLayout());
    private final JPanel footer = new JPanel(new BorderLayout());

    protected BaseView(String viewName) {
        this(viewName, new DefaultTheme());
    }

    protected BaseView(String viewName, Theme theme) {
        this.viewName = viewName;
        this.theme = theme == null ? new DefaultTheme() : theme;
        setPreferredSize(this.theme.windowDefault());
        setLayout(new BorderLayout(0, 0));
        setOpaque(false);

        // Build the page template
        JPanel page = new GradientPanel(theme);
        page.setLayout(new BorderLayout());

        header.setOpaque(false);
        content.setOpaque(false);
        footer.setOpaque(false);

        // Content padding
        content.setBorder(BorderFactory.createEmptyBorder(
                theme.contentInsets().top,
                theme.contentInsets().left,
                theme.contentInsets().bottom,
                theme.contentInsets().right));

        page.add(header, BorderLayout.NORTH);
        page.add(content, BorderLayout.CENTER);
        page.add(footer, BorderLayout.SOUTH);
        add(page, BorderLayout.CENTER);
    }

    /**
     * Call in subclass for a back button.
     * 
     * @param action the ActionListener for the back button
     * @return the panel containing the back button
     */
    protected JPanel createBackButtonPanel(java.awt.event.ActionListener action) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, BACKBTN_H_GAP, BACKBTN_V_GAP));
        panel.setOpaque(false);

        // Create a container for the back button with hover effect
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BorderLayout());
        buttonContainer.setOpaque(false);
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(BTN_CONTAINER_PADDING, BTN_CONTAINER_PADDING,
                BTN_CONTAINER_PADDING, BTN_CONTAINER_PADDING));

        // Style the back button
        backButton.setText("← Back");
        backButton.setFont(theme.button());
        backButton.setForeground(new Color(RGB, RGB, RGB));
        backButton.setBackground(theme.primary());
        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(theme.primary().darker(), BTN_LINE_BORDER_WIDTH),
                BorderFactory.createEmptyBorder(BTN_PAD_VERTICAL, BTN_PAD_HORIZONTAL,
                        BTN_PAD_VERTICAL, BTN_PAD_HORIZONTAL)));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(theme.primary().brighter());
                backButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(theme.primary());
                backButton.setForeground(new Color(RGB, RGB, RGB));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                backButton.setBackground(theme.secondary());
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                backButton.setBackground(theme.primary().brighter());
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

    /**
     * Gets the name of the view.
     *
     * @return the view name
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Build the generic panel for the app.
     * 
     * @return the coloured panel
     */
    protected static class GradientPanel extends JPanel {
        private final Theme theme;

        GradientPanel(Theme theme) {
            super(new BorderLayout(PANEL_GAP, PANEL_GAP));
            this.theme = theme;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            Color c1 = theme.secondary();
            Color c2 = theme.primary();
            g2d.setPaint(new java.awt.GradientPaint(0, 0, c1, width, height, c2));
            g2d.fillRect(0, 0, width, height);
        }
    }

    /**
     * Backward-compatible helper for views that previously created a gradient
     * content panel.
     * Prefer composing your own container with GradientPanel if creating new views.
     */
    protected JPanel createGradientContentPanel() {
        return new GradientPanel(theme);
    }

}
