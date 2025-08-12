package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import entity.CompanyDetails;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.company_details.CompanyDetailsController;
import interfaceadapter.company_details.CompanyDetailsState;
import interfaceadapter.company_details.CompanyDetailsViewModel;
import view.ui.UiConstants;

/**
 * The View for Company Details.
 */
public class CompanyDetailsView extends BaseView {

    private static final int HEADER_MAX_HEIGHT = 140;
    private static final int TWO = 2;
    private static final int BORDER_THICK = 3;
    private static final int TWENTY = 20;
    private static final int THIRTY_TWO = 32;
    private static final int MILLION = 1_000_000;
    private static final int FIVE = 5;
    private static final int TEN = 10;
    private static final int FIFTEEN = 15;
    private static final int FIFTY = 50;
    private static final int TWENTY_FIVE = 25;
    private static final int EIGHT = 8;
    private static final int TWELVE = 12;
    private static final int THIRTY = 30;

    private final CompanyDetailsViewModel companyDetailsViewModel;
    private final CompanyDetailsController companyDetailsController;
    private final BackNavigationHelper backNavigationHelper;

    private JPanel mainContainer;
    private JScrollPane scrollPane;

    public CompanyDetailsView(CompanyDetailsViewModel companyDetailsViewModel,
            CompanyDetailsController companyDetailsController,
            ViewManagerModel viewManagerModel) {
        super("company_details");
        this.companyDetailsViewModel = companyDetailsViewModel;
        this.companyDetailsController = companyDetailsController;
        this.backNavigationHelper = new BackNavigationHelper(viewManagerModel);

        initializeView();

        companyDetailsViewModel.addPropertyChangeListener(evt -> updateView());
    }

    private void initializeView() {
        // Header with back button only (title is dynamic inside content)
        header.add(createBackButtonPanel(ex -> backNavigationHelper.goBackToPortfolios()), BorderLayout.WEST);

        // Content container
        mainContainer = new JPanel(new BorderLayout());
        mainContainer.setOpaque(false);

        // Scrollable content area
        scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainContainer.add(scrollPane, BorderLayout.CENTER);
        content.add(mainContainer, BorderLayout.CENTER);
    }

    private void updateView() {
        CompanyDetailsState state = companyDetailsViewModel.getState();

        if (state.getErrorMessage() != null) {
            showErrorView(state.getErrorMessage());
        }
        else if (state.getCompanyDetails() != null) {
            CompanyDetails details = state.getCompanyDetails();
            showCompanyDetails(details);
        }
        else {
            showLoadingView();
        }
    }

    private void showErrorView(String errorMessage) {
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
        errorPanel.setOpaque(false);
        errorPanel.setBorder(BorderFactory.createEmptyBorder(FIFTY, FIFTY, FIFTY, FIFTY));

        JLabel errorLabel = new JLabel("Error: " + errorMessage);
        errorLabel.setFont(UiConstants.Fonts.HEADING);
        errorLabel.setForeground(UiConstants.Colors.DANGER);
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
        loadingPanel.setBorder(BorderFactory.createEmptyBorder(FIFTY, FIFTY, FIFTY, FIFTY));

        JLabel loadingLabel = new JLabel("Loading company details...");
        loadingLabel.setFont(UiConstants.Fonts.BODY);
        loadingLabel.setForeground(UiConstants.Colors.SURFACE_BG);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadingPanel.add(loadingLabel);

        scrollPane.setViewportView(loadingPanel);
        revalidate();
        repaint();
    }

    private void showCompanyDetails(CompanyDetails details) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(TWENTY, THIRTY, TWENTY, THIRTY));

        // Company header
        mainPanel.add(createCompanyHeader(details));
        mainPanel.add(Box.createVerticalStrut(TWENTY));

        // Basic info section
        mainPanel.add(createBasicInfoSection(details));
        mainPanel.add(Box.createVerticalStrut(TWENTY));

        // Financial metrics section
        mainPanel.add(createFinancialMetricsSection(details));
        mainPanel.add(Box.createVerticalStrut(TWENTY));

        // Peers section
        if (!details.getPeers().isEmpty()) {
            mainPanel.add(createPeersSection(details.getPeers()));
            mainPanel.add(Box.createVerticalStrut(TWENTY));
        }

        scrollPane.setViewportView(mainPanel);
        revalidate();
        repaint();
    }

    private JPanel createCompanyHeader(CompanyDetails details) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, HEADER_MAX_HEIGHT));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY, 2, true),
                BorderFactory.createEmptyBorder(TWENTY, TWENTY, TWENTY, TWENTY)));

        // Left side: Company info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(details.getName());
        nameLabel.setFont(UiConstants.Fonts.TITLE);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel symbolLabel = new JLabel("(" + details.getSymbol() + ")");
        symbolLabel.setFont(UiConstants.Fonts.BODY);
        symbolLabel.setForeground(UiConstants.Colors.SURFACE_BG);
        symbolLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel exchangeLabel = new JLabel("📈 " + details.getExchange());
        exchangeLabel.setFont(UiConstants.Fonts.SMALL);
        exchangeLabel.setForeground(UiConstants.Colors.SURFACE_BG);
        exchangeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(FIVE));
        infoPanel.add(symbolLabel);
        infoPanel.add(Box.createVerticalStrut(FIVE));
        infoPanel.add(exchangeLabel);

        headerPanel.add(infoPanel, BorderLayout.WEST);

        // Right side: Current Stock Price - Make it prominent and right-aligned
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setOpaque(false);

        JLabel priceTitle = new JLabel("Current Price");
        priceTitle.setFont(UiConstants.Fonts.FORM);
        priceTitle.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        priceTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);

        String priceText = details.getCurrentPrice() > 0 ? String.format("$%.2f", details.getCurrentPrice()) : "N/A";
        JLabel priceLabel = new JLabel(priceText);
        priceLabel.setFont(new Font("Sans Serif", Font.BOLD, THIRTY_TWO));
        priceLabel.setForeground(UiConstants.Colors.SUCCESS);
        priceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel currencyLabel = new JLabel(details.getCurrency());
        currencyLabel.setFont(UiConstants.Fonts.SMALL);
        currencyLabel.setForeground(UiConstants.Colors.TEXT_MUTED);
        currencyLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        pricePanel.add(priceTitle);
        pricePanel.add(Box.createVerticalStrut(FIVE));
        pricePanel.add(priceLabel);
        pricePanel.add(Box.createVerticalStrut(TWO));
        pricePanel.add(currencyLabel);

        headerPanel.add(pricePanel, BorderLayout.EAST);

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
            addInfoRow(section, "Market Cap:", currencyFormat.format(details.getMarketCapitalization() * MILLION));
        }

        if (details.getShareOutstanding() > 0) {
            addInfoRow(section, "Shares Outstanding:", numberFormat.format(details.getShareOutstanding() * MILLION));
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

        // Section palette (centralized)
        Color borderColor = switch (title) {
            case "Company Information" -> UiConstants.Palette.SECTION_INFO;
            case "Financial Metrics" -> UiConstants.Palette.SECTION_METRICS;
            case "Peer Companies" -> UiConstants.Palette.SECTION_PEERS;
            default -> UiConstants.Palette.SECTION_DEFAULT;
        };

        section.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, BORDER_THICK, true),
                BorderFactory.createEmptyBorder(TWENTY, TWENTY, TWENTY, TWENTY)));
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        String icon = switch (title) {
            case "Company Information" -> "🏢 ";
            case "Financial Metrics" -> "📊 ";
            case "Peer Companies" -> "🤝 ";
            default -> "ℹ️ ";
        };

        JLabel titleLabel = new JLabel(icon + title);
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, TWENTY));
        titleLabel.setForeground(borderColor.brighter());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        section.add(titleLabel);
        section.add(Box.createVerticalStrut(FIFTEEN));

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
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, TWENTY_FIVE));

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(UiConstants.Fonts.SMALL);
        labelComponent.setForeground(UiConstants.Colors.TEXT_MUTED);
        labelComponent.setHorizontalAlignment(SwingConstants.CENTER);
        labelComponent.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(UiConstants.Fonts.BODY);
        valueComponent.setForeground(UiConstants.Colors.SURFACE_BG);
        valueComponent.setHorizontalAlignment(SwingConstants.CENTER);
        valueComponent.setAlignmentX(Component.CENTER_ALIGNMENT);

        row.add(Box.createHorizontalGlue());
        row.add(labelComponent);
        row.add(Box.createHorizontalStrut(TEN));
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
                peersFlow.add(Box.createHorizontalStrut(TEN));
            }
        }

        peersFlow.add(Box.createHorizontalGlue());

        section.add(peersFlow);
        return section;
    }

    private JLabel createClickablePeerLabel(String ticker) {
        JLabel peerLabel = new JLabel(ticker + " →");
        peerLabel.setFont(UiConstants.Fonts.BODY);
        peerLabel.setForeground(Color.WHITE);
        peerLabel.setBackground(UiConstants.Colors.PRIMARY);
        peerLabel.setOpaque(true);
        peerLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiConstants.Colors.SECONDARY.brighter(), TWO, true),
                BorderFactory.createEmptyBorder(EIGHT, TWELVE, EIGHT, TWELVE)));
        peerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        peerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listeners for click and hover effects
        peerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                companyDetailsController.execute(ticker, "company_details");
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                peerLabel.setBackground(UiConstants.PRESSED_COLOUR);
                peerLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY.brighter(), BORDER_THICK, true),
                        BorderFactory.createEmptyBorder(EIGHT, TWELVE, EIGHT, TWELVE)));
                peerLabel.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                peerLabel.setBackground(UiConstants.Colors.PRIMARY);
                peerLabel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UiConstants.Colors.SECONDARY.brighter(), TWO, true),
                        BorderFactory.createEmptyBorder(EIGHT, TWELVE, EIGHT, TWELVE)));
                peerLabel.repaint();
            }
        });

        return peerLabel;
    }
}
