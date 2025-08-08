package view;

import interface_adapter.navigation.NavigationController;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio_hub.PortfolioHubController;
import interface_adapter.portfolio_hub.PortfolioHubState;
import interface_adapter.portfolio_hub.PortfolioHubViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import view.ui.UiConstants;

/**
 * Custom layout manager that wraps components like FlowLayout but respects
 * container width
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
    @SuppressWarnings("unused")
    private final NavigationController navigationController;

    private JPanel buttonPanel;

    public PortfolioHubView(PortfolioHubViewModel portfoliosViewModel, PortfolioHubController portfolioHubController,
            PortfolioController portfolioController, NavigationController navigationController) {
        super("portfolio hub");
        this.portfoliosViewModel = portfoliosViewModel;
        this.portfolioHubController = portfolioHubController;
        this.portfolioController = portfolioController;
        this.navigationController = navigationController;

        // Header
        JLabel titleLabel = new JLabel("Portfolios");
        titleLabel.setFont(UiConstants.Fonts.TITLE);
        titleLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(titleLabel);
        header.add(headerLeft, BorderLayout.WEST);

        // Main canvas panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(UiConstants.Spacing.XL, UiConstants.Spacing.XL,
                UiConstants.Spacing.XL, UiConstants.Spacing.XL));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(UiConstants.Spacing.MD, 0, UiConstants.Spacing.MD, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // Create button section
        JPanel createButtonSection = createModernCreateButtonPanel();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        mainPanel.add(createButtonSection, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.LG), gbc);

        // Your Portfolios label
        JLabel portfoliosLabel = new JLabel("Your Portfolios:");
        portfoliosLabel.setFont(UiConstants.Fonts.HEADING);
        portfoliosLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        gbc.gridy++;
        mainPanel.add(portfoliosLabel, gbc);

        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.MD), gbc);

        // Portfolio buttons panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new WrapLayout(FlowLayout.CENTER, UiConstants.Spacing.LG, UiConstants.Spacing.LG));
        buttonPanel.setBackground(UiConstants.Colors.CANVAS_BG);
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

        // Route via BaseView content
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        content.add(scrollPane, BorderLayout.CENTER);

        registerViewModelListener();
    }

    private JPanel createModernCreateButtonPanel() {
        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        container.setBackground(UiConstants.Colors.CANVAS_BG);

        JButton createButton = new JButton("New Portfolio");
        createButton.setFont(UiConstants.Fonts.BUTTON);
        createButton.setBackground(UiConstants.Colors.SUCCESS);
        createButton.setForeground(UiConstants.Colors.ON_PRIMARY);
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createButton.addActionListener(evt -> {
            PortfolioHubState portfoliosState = this.portfoliosViewModel.getState();
            this.portfolioHubController.routeToCreate(portfoliosState.getUsername());
        });

        createButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createButton.setBackground(new Color(30, 120, 30));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createButton.setBackground(UiConstants.Colors.SUCCESS);
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

            Dimension cardSize = UiConstants.Sizes.CARD;
            Color cardBg = new Color(255, 94, 0);
            Color cardBgHover = new Color(255, 120, 40);

            for (String portfolio : portfolios.keySet()) {
                final String portfolioId = portfolios.get(portfolio);
                final String portfolioName = portfolio;
                final String username = portfoliosState.getUsername();

                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(cardBg);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK, 2),
                        BorderFactory.createEmptyBorder(14, 18, 14, 18)));
                card.setPreferredSize(cardSize);
                card.setMaximumSize(cardSize);
                card.setMinimumSize(cardSize);

                JButton openButton = new JButton(portfolioName);
                openButton.setFont(UiConstants.Fonts.HEADING);
                openButton.setForeground(Color.BLACK);
                openButton.setBackground(cardBg);
                openButton.setFocusPainted(false);
                openButton.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
                openButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                openButton.addActionListener(nxtEvt -> {
                    portfolioController.execute(username, portfolioId, portfolioName);
                });

                card.add(openButton, BorderLayout.CENTER);

                java.awt.event.MouseAdapter hover = new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        card.setBackground(cardBgHover);
                        openButton.setBackground(cardBgHover);
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        card.setBackground(cardBg);
                        openButton.setBackground(cardBg);
                    }
                };
                openButton.addMouseListener(hover);
                card.addMouseListener(hover);

                buttonPanel.add(card);
            }

            buttonPanel.revalidate();
            buttonPanel.repaint();
        });
    }
}
