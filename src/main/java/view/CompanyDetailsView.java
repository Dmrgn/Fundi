package view;

import entity.CompanyDetails;
import interface_adapter.company_details.CompanyDetailsController;
import interface_adapter.company_details.CompanyDetailsState;
import interface_adapter.company_details.CompanyDetailsViewModel;
import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The View for Company Details.
 */
public class CompanyDetailsView extends BaseView {

    private final CompanyDetailsViewModel companyDetailsViewModel;
    private final CompanyDetailsController companyDetailsController;
    private final NavigationController navigationController;

    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private List<String> navigationBreadcrumbs = new ArrayList<>();

    public CompanyDetailsView(CompanyDetailsViewModel companyDetailsViewModel,
            CompanyDetailsController companyDetailsController,
            NavigationController navigationController) {
        super("company_details");
        this.companyDetailsViewModel = companyDetailsViewModel;
        this.companyDetailsController = companyDetailsController;
        this.navigationController = navigationController;

        // Initialize UI
        initializeView();

        // Set up property change listener
        companyDetailsViewModel.addPropertyChangeListener(evt -> updateView());
    }

    private void initializeView() {
        setLayout(new BorderLayout());

        // Create main content panel
        contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BorderLayout());

        // Create back button panel
        JPanel topPanel = createTopPanel();
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Create scrollable content area
        scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Create back button panel
        JPanel backButtonPanel = createBackButtonPanel(e -> {
            // Use navigation controller to go back to previous view
            navigationController.goBack();
        });

        // Create breadcrumb panel
        JPanel breadcrumbPanel = createBreadcrumbPanel();

        topPanel.add(backButtonPanel, BorderLayout.WEST);
        topPanel.add(breadcrumbPanel, BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel createBreadcrumbPanel() {
        JPanel breadcrumbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        breadcrumbPanel.setOpaque(false);

        if (!navigationBreadcrumbs.isEmpty()) {
            JLabel homeLabel = new JLabel("🏠 Dashboard");
            homeLabel.setFont(new Font("Sans Serif", Font.PLAIN, 12));
            homeLabel.setForeground(Color.LIGHT_GRAY);
            breadcrumbPanel.add(homeLabel);

            for (int i = 0; i < navigationBreadcrumbs.size(); i++) {
                String symbol = navigationBreadcrumbs.get(i);

                // Add separator arrow
                JLabel arrow = new JLabel(" → ");
                arrow.setFont(new Font("Sans Serif", Font.BOLD, 12));
                arrow.setForeground(Color.CYAN);
                breadcrumbPanel.add(arrow);

                // Create clickable breadcrumb for previous companies
                if (i < navigationBreadcrumbs.size() - 1) {
                    JLabel breadcrumbLabel = createClickableBreadcrumb(symbol, i);
                    breadcrumbPanel.add(breadcrumbLabel);
                } else {
                    // Current company (not clickable)
                    JLabel currentLabel = new JLabel(symbol);
                    currentLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
                    currentLabel.setForeground(Color.WHITE);
                    breadcrumbPanel.add(currentLabel);
                }
            }
        }

        return breadcrumbPanel;
    }

    private JLabel createClickableBreadcrumb(String symbol, int breadcrumbIndex) {
        JLabel breadcrumbLabel = new JLabel(symbol);
        breadcrumbLabel.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        breadcrumbLabel.setForeground(new Color(173, 216, 230)); // Light blue
        breadcrumbLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        breadcrumbLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Navigate back to this breadcrumb level
                navigateToBreadcrumb(breadcrumbIndex);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                breadcrumbLabel.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                breadcrumbLabel.setForeground(new Color(173, 216, 230));
            }
        });

        return breadcrumbLabel;
    }

    private void navigateToBreadcrumb(int targetIndex) {
        // Remove breadcrumbs after the target index
        if (targetIndex < navigationBreadcrumbs.size() - 1) {
            String targetSymbol = navigationBreadcrumbs.get(targetIndex);

            // Trim breadcrumbs to target level
            navigationBreadcrumbs = navigationBreadcrumbs.subList(0, targetIndex + 1);

            // Navigate to the target company
            companyDetailsController.execute(targetSymbol, "company_details");
        }
    }

    private void updateView() {
        CompanyDetailsState state = companyDetailsViewModel.getState();

        if (state.getErrorMessage() != null) {
            showErrorView(state.getErrorMessage());
        } else if (state.getCompanyDetails() != null) {
            showCompanyDetails(state.getCompanyDetails());
        } else {
            showLoadingView();
        }
    }

    private void showErrorView(String errorMessage) {
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
        errorPanel.setOpaque(false);
        errorPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel errorLabel = new JLabel("Error: " + errorMessage);
        errorLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        errorPanel.add(errorLabel);

        scrollPane.setViewportView(errorPanel);
        revalidate();
        repaint();
    }

    private void showLoadingView() {
        JPanel loadingPanel = new JPanel();
        loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.Y_AXIS));
        loadingPanel.setOpaque(false);
        loadingPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel loadingLabel = new JLabel("Loading company details...");
        loadingLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadingPanel.add(loadingLabel);

        scrollPane.setViewportView(loadingPanel);
        revalidate();
        repaint();
    }

    private void showCompanyDetails(CompanyDetails details) {
        // Update breadcrumbs when showing new company details
        updateBreadcrumbs(details.getSymbol());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Company header
        mainPanel.add(createCompanyHeader(details));
        mainPanel.add(Box.createVerticalStrut(20));

        // Basic info section
        mainPanel.add(createBasicInfoSection(details));
        mainPanel.add(Box.createVerticalStrut(20));

        // Financial metrics section
        mainPanel.add(createFinancialMetricsSection(details));
        mainPanel.add(Box.createVerticalStrut(20));

        // News section removed

        // Peers section
        if (!details.getPeers().isEmpty()) {
            mainPanel.add(createPeersSection(details.getPeers()));
            mainPanel.add(Box.createVerticalStrut(20));
        }

        scrollPane.setViewportView(mainPanel);

        // Update the top panel to refresh breadcrumbs
        contentPanel.remove(0); // Remove old top panel
        JPanel newTopPanel = createTopPanel();
        contentPanel.add(newTopPanel, BorderLayout.NORTH, 0); // Add new top panel at index 0

        revalidate();
        repaint();
    }

    private void updateBreadcrumbs(String newSymbol) {
        // If this is a new symbol, add it to breadcrumbs
        if (navigationBreadcrumbs.isEmpty()
                || !navigationBreadcrumbs.get(navigationBreadcrumbs.size() - 1).equals(newSymbol)) {
            navigationBreadcrumbs.add(newSymbol);

            // Limit breadcrumb depth to prevent UI overflow
            if (navigationBreadcrumbs.size() > 5) {
                navigationBreadcrumbs.remove(0);
            }
        }
    }

    /**
     * Clears breadcrumbs when navigating from search or other non-company views
     */
    public void clearBreadcrumbs() {
        navigationBreadcrumbs.clear();
    }

    /**
     * Sets breadcrumbs when coming from navigation stack
     */
    public void setBreadcrumbs(List<String> breadcrumbs) {
        this.navigationBreadcrumbs = breadcrumbs != null ? new ArrayList<>(breadcrumbs) : new ArrayList<>();
    }

    private JPanel createCompanyHeader(CompanyDetails details) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Left side: Company info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(details.getName());
        nameLabel.setFont(new Font("Sans Serif", Font.BOLD, 28));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel symbolLabel = new JLabel("(" + details.getSymbol() + ")");
        symbolLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        symbolLabel.setForeground(new Color(173, 216, 230));
        symbolLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel exchangeLabel = new JLabel("📈 " + details.getExchange());
        exchangeLabel.setFont(new Font("Sans Serif", Font.ITALIC, 16));
        exchangeLabel.setForeground(Color.CYAN);
        exchangeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(symbolLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(exchangeLabel);

        headerPanel.add(infoPanel, BorderLayout.WEST);

        // Center: Current Stock Price - Make it prominent
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setOpaque(false);

        JLabel priceTitle = new JLabel("Current Price");
        priceTitle.setFont(new Font("Sans Serif", Font.BOLD, 14));
        priceTitle.setForeground(new Color(255, 215, 0)); // Gold color
        priceTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        String priceText = details.getCurrentPrice() > 0 ? String.format("$%.2f", details.getCurrentPrice()) : "N/A";
        JLabel priceLabel = new JLabel(priceText);
        priceLabel.setFont(new Font("Sans Serif", Font.BOLD, 32));
        priceLabel.setForeground(new Color(50, 205, 50)); // Lime green for price
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel currencyLabel = new JLabel(details.getCurrency());
        currencyLabel.setFont(new Font("Sans Serif", Font.ITALIC, 12));
        currencyLabel.setForeground(Color.LIGHT_GRAY);
        currencyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        pricePanel.add(priceTitle);
        pricePanel.add(Box.createVerticalStrut(5));
        pricePanel.add(priceLabel);
        pricePanel.add(Box.createVerticalStrut(2));
        pricePanel.add(currencyLabel);

        headerPanel.add(pricePanel, BorderLayout.CENTER);

        // Right side: Logo placeholder with better styling
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel("💼");
        logoLabel.setFont(new Font("Sans Serif", Font.PLAIN, 60));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoText = new JLabel("Company");
        logoText.setFont(new Font("Sans Serif", Font.BOLD, 12));
        logoText.setForeground(Color.LIGHT_GRAY);
        logoText.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoPanel.add(logoLabel);
        logoPanel.add(logoText);

        headerPanel.add(logoPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createBasicInfoSection(CompanyDetails details) {
        JPanel section = createSectionPanel("Company Information");

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        addInfoRow(section, "Industry:", details.getFinnhubIndustry());
        addInfoRow(section, "Country:", details.getCountry());
        addInfoRow(section, "Currency:", details.getCurrency());
        addInfoRow(section, "IPO Date:", details.getIpoDate());

        if (details.getMarketCapitalization() > 0) {
            addInfoRow(section, "Market Cap:", currencyFormat.format(details.getMarketCapitalization() * 1_000_000));
        }

        if (details.getShareOutstanding() > 0) {
            addInfoRow(section, "Shares Outstanding:", numberFormat.format(details.getShareOutstanding() * 1_000_000));
        }

        if (!details.getPhone().equals("N/A") && !details.getPhone().isEmpty()) {
            addInfoRow(section, "Phone:", details.getPhone());
        }

        if (!details.getWebUrl().equals("N/A") && !details.getWebUrl().isEmpty()) {
            addInfoRow(section, "Website:", details.getWebUrl());
        }

        return section;
    }

    private JPanel createFinancialMetricsSection(CompanyDetails details) {
        JPanel section = createSectionPanel("Financial Metrics");

        if (details.getPeRatio() > 0) {
            addInfoRow(section, "P/E Ratio:", String.format("%.2f", details.getPeRatio()));
        }

        if (details.getWeek52High() > 0) {
            addInfoRow(section, "52-Week High:", String.format("$%.2f", details.getWeek52High()));
        }

        if (details.getWeek52Low() > 0) {
            addInfoRow(section, "52-Week Low:", String.format("$%.2f", details.getWeek52Low()));
        }

        if (details.getBeta() > 0) {
            addInfoRow(section, "Beta:", String.format("%.2f", details.getBeta()));
        }

        return section;
    }

    private JPanel createSectionPanel(String title) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);

        // Different colored borders for different sections
        Color borderColor = switch (title) {
            case "Company Information" -> new Color(138, 43, 226); // Blue Violet
            case "Financial Metrics" -> new Color(255, 140, 0); // Dark Orange
            case "Peer Companies" -> new Color(30, 144, 255); // Dodger Blue
            default -> new Color(100, 149, 237); // Cornflower Blue
        };

        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 3, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Section title with icon and enhanced styling
        String icon = switch (title) {
            case "Company Information" -> "🏢 ";
            case "Financial Metrics" -> "📊 ";
            case "Peer Companies" -> "🤝 ";
            default -> "ℹ️ ";
        };

        JLabel titleLabel = new JLabel(icon + title);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
        titleLabel.setForeground(borderColor.brighter()); // Use lighter version of border color
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        section.add(titleLabel);
        section.add(Box.createVerticalStrut(15));

        // Center all child components added to this section
        section.setAlignmentX(Component.CENTER_ALIGNMENT);

        return section;
    }

    private void addInfoRow(JPanel parent, String label, String value) {
        if (value == null || value.trim().isEmpty() || value.equals("N/A")) {
            return;
        }

        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Sans Serif", Font.BOLD, 12));
        labelComponent.setForeground(Color.LIGHT_GRAY);
        labelComponent.setHorizontalAlignment(SwingConstants.CENTER);
        labelComponent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        valueComponent.setForeground(Color.WHITE);
        valueComponent.setHorizontalAlignment(SwingConstants.CENTER);
        valueComponent.setAlignmentX(Component.CENTER_ALIGNMENT);

        row.add(Box.createHorizontalGlue());
        row.add(labelComponent);
        row.add(Box.createHorizontalStrut(10));
        row.add(valueComponent);
        row.add(Box.createHorizontalGlue());

        parent.add(row);
    }

    private JPanel createPeersSection(List<String> peers) {
        JPanel section = createSectionPanel("Peer Companies");

        JPanel peersFlow = new JPanel();
        peersFlow.setLayout(new BoxLayout(peersFlow, BoxLayout.X_AXIS));
        peersFlow.setOpaque(false);
        peersFlow.setAlignmentX(Component.CENTER_ALIGNMENT);

        peersFlow.add(Box.createHorizontalGlue());

        for (int i = 0; i < peers.size(); i++) {
            String peer = peers.get(i);
            JLabel peerLabel = createClickablePeerLabel(peer);
            peersFlow.add(peerLabel);

            if (i < peers.size() - 1) {
                peersFlow.add(Box.createHorizontalStrut(10));
            }
        }

        peersFlow.add(Box.createHorizontalGlue());

        section.add(peersFlow);
        return section;
    }

    private JLabel createClickablePeerLabel(String ticker) {
        JLabel peerLabel = new JLabel(ticker + " →");
        peerLabel.setFont(new Font("Sans Serif", Font.BOLD, 13));
        peerLabel.setForeground(Color.WHITE);
        peerLabel.setBackground(new Color(70, 130, 180));
        peerLabel.setOpaque(true);
        peerLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.CYAN, 2, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        peerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        peerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listeners for click and hover effects
        peerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Navigate to the peer company's details
                companyDetailsController.execute(ticker, "company_details");
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                peerLabel.setBackground(new Color(100, 149, 237)); // Cornflower blue
                peerLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.YELLOW, 3, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
                peerLabel.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                peerLabel.setBackground(new Color(70, 130, 180)); // Steel blue
                peerLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.CYAN, 2, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
                peerLabel.repaint();
            }
        });

        return peerLabel;
    }
}
