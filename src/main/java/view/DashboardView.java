package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entity.SearchResult;
import interfaceadapter.company_details.CompanyDetailsController;
import interfaceadapter.dashboard.DashboardController;
import interfaceadapter.dashboard.DashboardState;
import interfaceadapter.dashboard.DashboardViewModel;
import interfaceadapter.main.MainState;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.search.SearchController;
import interfaceadapter.search.SearchState;
import interfaceadapter.search.SearchViewModel;
import view.dashboard.PortfolioChartPanel;
import view.dashboard.SearchResultsPanel;
import view.dashboard.SearchSectionPanel;
import view.dashboard.UsernamePanel;
import view.dashboard.WelcomePanel;
import view.ui.UiConstants;
import view.util.PanelFactory;

public class DashboardView extends AbstractBaseView {
    private final MainViewModel mainViewModel;
    private final SearchViewModel searchViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final DashboardController dashboardController;
    private final PortfolioChartPanel portfolioChartPanel;
    private final SearchResultsPanel searchResultsPanel;
    private boolean hasSearched;

    public DashboardView(MainViewModel mainViewModel, SearchController searchController,
            SearchViewModel searchViewModel, DashboardViewModel dashboardViewModel,
            DashboardController dashboardController,
            interfaceadapter.navigation.NavigationController navigationController,
            CompanyDetailsController companyDetailsController) {
        super("dashboard");
        this.mainViewModel = mainViewModel;
        this.searchViewModel = searchViewModel;
        this.dashboardViewModel = dashboardViewModel;
        this.dashboardController = dashboardController;

        getHeader().add(PanelFactory.createHeader("Dashboard"), BorderLayout.WEST);

        JPanel mainPanel = PanelFactory.createCanvasPanelWithInsets();
        GridBagConstraints gbc = PanelFactory.defaultGbc();

        // Create listener for search results
        SearchResultsPanel.Listener resultsListener = new SearchResultsPanel.Listener() {
            @Override
            public void onClear() {
                searchResultsPanel.setResults(List.of(), this);
                searchResultsPanel.setVisible(false);
            }

            @Override
            public void onOpenDetails(String symbol) {
                companyDetailsController.execute(symbol, "dashboard");
            }
        };

        // Add components
        addPanel(mainPanel, new WelcomePanel(), gbc, 0);
        addPanel(mainPanel, new SearchSectionPanel(query -> {
            hasSearched = true;
            searchController.execute(query);
        }), gbc, 1);

        searchResultsPanel = new SearchResultsPanel(resultsListener);
        searchResultsPanel.setVisible(false);
        addPanel(mainPanel, searchResultsPanel, gbc, 2);

        portfolioChartPanel = new PortfolioChartPanel();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        addPanel(mainPanel, portfolioChartPanel, gbc, 3);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        addPanel(mainPanel, new UsernamePanel(mainViewModel), gbc, 4);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContent().add(scrollPane, BorderLayout.CENTER);

        setupListeners(resultsListener);
    }

    private void addPanel(JPanel parent, JPanel child, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        parent.add(child, gbc);
    }

    private void setupListeners(SearchResultsPanel.Listener resultsListener) {
        searchViewModel.addPropertyChangeListener(evt -> {
            SearchState state = searchViewModel.getState();
            List<SearchResult> results = state.getSearchResults();
            if (hasSearched) {
                searchResultsPanel.setResults(results != null ? results : List.of(), resultsListener);
                searchResultsPanel.setVisible(true);
                searchResultsPanel.revalidate();
                searchResultsPanel.repaint();
            }
        });

        dashboardViewModel.addPropertyChangeListener(evt -> {
            DashboardState state = dashboardViewModel.getState();
            portfolioChartPanel.updateChart(state.getPortfolioValueHistory());
        });

        mainViewModel.addPropertyChangeListener(evt -> {
            MainState mainState = mainViewModel.getState();
            if (mainState.getUsername() != null && !mainState.getUsername().isEmpty()) {
                DashboardState dashboardState = dashboardViewModel.getState();
                dashboardState.setUsername(mainState.getUsername());
                dashboardViewModel.setState(dashboardState);
                dashboardController.execute(mainState.getUsername());
            }
        });
    }
}
