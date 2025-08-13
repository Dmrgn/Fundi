package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entity.CompanyDetails;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.company_details.CompanyDetailsController;
import interfaceadapter.company_details.CompanyDetailsState;
import interfaceadapter.company_details.CompanyDetailsViewModel;
import view.companydetails.HeaderPanel;
import view.companydetails.InfoPanel;
import view.companydetails.MetricsPanel;
import view.companydetails.PeersPanel;
import view.ui.UiConstants;

/**
 * The View for Company Details.
 */
public class CompanyDetailsView extends AbstractBaseView {

    private static final int BORDER_THICK = 3;
    private static final int TWENTY = 20;
    // constants mostly moved into sub-panels
    private static final int FIFTEEN = 15;
    private static final int FIFTY = 50;
    private static final int THIRTY = 30;

    private final CompanyDetailsViewModel companyDetailsViewModel;
    private final BackNavigationHelper backNavigationHelper;

    private JPanel mainContainer;
    private JScrollPane scrollPane;
    // Sub-panels for SRP
    private final HeaderPanel headerPanel = new HeaderPanel();
    private final InfoPanel infoPanel = new InfoPanel();
    private final MetricsPanel metricsPanel = new MetricsPanel();
    private final PeersPanel peersPanel;

    public CompanyDetailsView(CompanyDetailsViewModel companyDetailsViewModel,
            CompanyDetailsController companyDetailsController,
            ViewManagerModel viewManagerModel) {
        super("company_details");
        this.companyDetailsViewModel = companyDetailsViewModel;
        this.backNavigationHelper = new BackNavigationHelper(viewManagerModel);
        this.peersPanel = new PeersPanel(companyDetailsController);

        initializeView();

        companyDetailsViewModel.addPropertyChangeListener(evt -> updateView());
    }

    private void initializeView() {
        // Header with back button only (title is dynamic inside content)
        getHeader().add(createBackButtonPanel(ex -> backNavigationHelper.goBackToPortfolios()), BorderLayout.WEST);

        // Content container
        mainContainer = new JPanel(new BorderLayout());
        mainContainer.setOpaque(false);

        // Scrollable content area
        scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        // Keep the viewport and scrollpane transparent to avoid white bars
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainContainer.add(scrollPane, BorderLayout.CENTER);
        getContent().add(mainContainer, BorderLayout.CENTER);
    }

    private void updateView() {
        CompanyDetailsState state = companyDetailsViewModel.getState();

        if (state.getErrorMessage() != null) {
            showErrorView(state.getErrorMessage());
        } else if (state.getCompanyDetails() != null) {
            CompanyDetails details = state.getCompanyDetails();
            showCompanyDetails(details);
        } else {
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
        headerPanel.setData(details);
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(TWENTY));

        // Basic info section
        JPanel infoSection = createSectionPanel("Company Information");
        infoSection.add(infoPanel);
        infoPanel.setData(details);
        mainPanel.add(infoSection);
        mainPanel.add(Box.createVerticalStrut(TWENTY));

        // Financial metrics section
        JPanel metricsSection = createSectionPanel("Financial Metrics");
        metricsSection.add(metricsPanel);
        metricsPanel.setData(details);
        mainPanel.add(metricsSection);
        mainPanel.add(Box.createVerticalStrut(TWENTY));

        // Peers section
        if (details.getPeers() != null && !details.getPeers().isEmpty()) {
            JPanel peersSection = createSectionPanel("Peer Companies");
            peersPanel.setPeers(details.getPeers());
            peersSection.add(peersPanel);
            mainPanel.add(peersSection);
            mainPanel.add(Box.createVerticalStrut(TWENTY));
        }

        scrollPane.setViewportView(mainPanel);
        revalidate();
        repaint();
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

}
