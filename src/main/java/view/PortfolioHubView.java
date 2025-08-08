package view;

import interface_adapter.navigation.NavigationController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio_hub.PortfolioHubController;
import interface_adapter.portfolio_hub.PortfolioHubState;
import interface_adapter.portfolio_hub.PortfolioHubViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Custom layout manager that wraps components like FlowLayout but respects container width
 */
class WrapLayout extends FlowLayout {
    public WrapLayout() {
        super();
    }

    public WrapLayout(int align) {
        super(align);
    }

    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension minimum = layoutSize(target, false);
        minimum.width -= (getHgap() + 1);
        return minimum;
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getSize().width;
            Container container = target;

            while (container.getSize().width == 0 && container.getParent() != null) {
                container = container.getParent();
            }
            targetWidth = container.getSize().width;

            if (targetWidth == 0) {
                targetWidth = Integer.MAX_VALUE;
            }

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
            int maxWidth = targetWidth - horizontalInsetsAndGap;

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);
                if (m.isVisible()) {
                    Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                    if (rowWidth + d.width > maxWidth) {
                        addRow(dim, rowWidth, rowHeight);
                        rowWidth = d.width;
                        rowHeight = d.height;
                    } else {
                        rowWidth += hgap + d.width;
                        rowHeight = Math.max(rowHeight, d.height);
                    }
                }
            }
            addRow(dim, rowWidth, rowHeight);

            dim.width = Math.max(dim.width, rowWidth);
            dim.height += vgap;

            Container scrollPane = target.getParent();
            if (scrollPane instanceof JViewport) {
                if (scrollPane.getParent() instanceof JScrollPane) {
                    scrollPane = scrollPane.getParent();
                }
            }

            dim.width = Math.min(dim.width, targetWidth);

            Insets targetInsets = target.getInsets();
            dim.width += targetInsets.left + targetInsets.right;
            dim.height += targetInsets.top + targetInsets.bottom + vgap;

            return dim;
        }
    }

    private void addRow(Dimension dim, int rowWidth, int rowHeight) {
        dim.width = Math.max(dim.width, rowWidth);
        if (dim.height > 0) {
            dim.height += getVgap();
        }
        dim.height += rowHeight;
    }
}

/**
 * The View for the Portfolio Hub Use Case.
 */
public class PortfolioHubView extends BaseView {
    private final PortfolioHubViewModel portfoliosViewModel;
    private final PortfolioHubController portfolioHubController;
    private final PortfolioController portfolioController;
    private final NavigationController navigationController;

    private JPanel buttonPanel;

    public PortfolioHubView(PortfolioHubViewModel portfoliosViewModel, PortfolioHubController portfolioHubController,
                            PortfolioController portfolioController, NavigationController navigationController) {
        super("portfolio hub");
        this.portfoliosViewModel = portfoliosViewModel;
        this.portfolioHubController = portfolioHubController;
        this.portfolioController = portfolioController;
        this.navigationController = navigationController;

        // Use modern layout with GridBagLayout
        this.setBackground(new Color(30, 60, 120));
        this.setLayout(new GridBagLayout());

        // Main white panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST; // left align
        gbc.insets = new Insets(8, 0, 8, 0); // tighter spacing
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // Title
        JLabel titleLabel = new JLabel("Portfolios");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(12), gbc);

        // Create button section
        JPanel createButtonSection = createModernCreateButtonPanel();
        gbc.gridy++;
        mainPanel.add(createButtonSection, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(12), gbc);

        // Your Portfolios label
        JLabel portfoliosLabel = new JLabel("Your Portfolios:");
        portfoliosLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        portfoliosLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy++;
        mainPanel.add(portfoliosLabel, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(8), gbc);

        // Portfolio buttons panel
        buttonPanel = new JPanel();
        // Use custom grid layout that wraps dynamically and centers rows
        buttonPanel.setLayout(new WrapLayout(FlowLayout.CENTER, 12, 12));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        // Add component listener to trigger relayout on resize
        buttonPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                buttonPanel.revalidate();
            }
        });
        
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to a scroll pane for overflow handling
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(new Color(30, 60, 120)); // UiConstants.PRIMARY_COLOUR
        scrollPane.getViewport().setBackground(new Color(30, 60, 120));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Add scroll pane to view with proper resizing
        GridBagConstraints viewGbc = new GridBagConstraints();
        viewGbc.fill = GridBagConstraints.BOTH;
        viewGbc.weightx = 1.0;
        viewGbc.weighty = 1.0;
        this.add(scrollPane, viewGbc);

        registerViewModelListener();
    }

    private JPanel createModernCreateButtonPanel() {
        // Left-align the New Portfolio button under the title
        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        container.setBackground(new Color(245, 245, 245));
        
        JButton createButton = new JButton("New Portfolio");
        createButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        // Green button per spec
        createButton.setBackground(new Color(34, 139, 34)); // ForestGreen
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createButton.addActionListener(evt -> {
            PortfolioHubState portfoliosState = this.portfoliosViewModel.getState();
            this.portfolioHubController.routeToCreate(portfoliosState.getUsername());
        });

        // Hover effect shades of green
        createButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createButton.setBackground(new Color(30, 120, 30));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createButton.setBackground(new Color(34, 139, 34));
            }
        });

        container.add(createButton);
        return container;
    }

    private void registerViewModelListener() {
        portfoliosViewModel.addPropertyChangeListener(evt -> {
            buttonPanel.removeAll();
            PortfolioHubState portfoliosState = portfoliosViewModel.getState();
            Map<String, String> portfolios = portfoliosState.getPortfolios();

            // Deep neon orange cards, taller and slightly wider than tall
            Dimension cardSize = new Dimension(520, 180); // wider than tall
            Color neonOrange = new Color(255, 94, 0); // vivid orange
            Color neonOrangeHover = new Color(255, 120, 40);

            for (String portfolio : portfolios.keySet()) {
                // Capture portfolio ID for this iteration
                final String portfolioId = portfolios.get(portfolio);
                final String portfolioName = portfolio;
                final String username = portfoliosState.getUsername();
                
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(neonOrange);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLACK, 2),
                    BorderFactory.createEmptyBorder(14, 18, 14, 18)
                ));
                card.setPreferredSize(cardSize);
                card.setMaximumSize(cardSize);
                card.setMinimumSize(cardSize);

                JButton openButton = new JButton(portfolioName);
                openButton.setFont(new Font("SansSerif", Font.BOLD, 18));
                openButton.setForeground(Color.BLACK);
                openButton.setBackground(neonOrange);
                openButton.setFocusPainted(false);
                openButton.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
                openButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                // Add action listener FIRST
                openButton.addActionListener(nxtEvt -> {
                    portfolioController.execute(username, portfolioId, portfolioName);
                });

                card.add(openButton, BorderLayout.CENTER);

                // Hover: lighten orange
                java.awt.event.MouseAdapter hover = new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        card.setBackground(neonOrangeHover);
                        openButton.setBackground(neonOrangeHover);
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        card.setBackground(neonOrange);
                        openButton.setBackground(neonOrange);
                    }
                };
                openButton.addMouseListener(hover);
                card.addMouseListener(hover);

                // Add card directly; FlowLayout will wrap into rows
                buttonPanel.add(card);
            }
            
            buttonPanel.revalidate();
            buttonPanel.repaint();
        });
    }
}
