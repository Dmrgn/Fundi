package app;

import java.awt.CardLayout;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.DBPortfoliosDataAccessObject;
import data_access.DBStockDataAccessObject;
import data_access.DBTransactionDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.NavigationState;
import interface_adapter.ViewManagerModel;
import interface_adapter.analysis.AnalysisController;
import interface_adapter.analysis.AnalysisViewModel;
import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.create.CreateController;
import interface_adapter.create.CreateViewModel;
import interface_adapter.history.HistoryController;
import interface_adapter.history.HistoryViewModel;
import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationPresenter;
import interface_adapter.news.NewsController;
import interface_adapter.news.NewsViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.portfolio_hub.PortfolioHubController;
import interface_adapter.portfolio_hub.PortfolioHubViewModel;
import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendViewModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import interface_adapter.sell.SellController;
import interface_adapter.sell.SellViewModel;
import interface_adapter.shortsell.ShortController;
import interface_adapter.shortsell.ShortPresenter;
import interface_adapter.shortsell.ShortViewModel;
import use_case.shortsell.ShortInputBoundary;
import use_case.shortsell.ShortInteractor;
import use_case.shortsell.ShortOutputBoundary;
import use_case.shortsell.ShortStockDataAccessInterface;
import use_case.shortsell.ShortTransactionDataAccessInterface;
import view.ShortView;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginUserDataAccessInterface;
import use_case.navigation.NavigationInteractor;
import use_case.navigation.NavigationOutputBoundary;
import use_case.search.GetMatches;
import use_case.search.SearchDataAccessInterface;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchOutputBoundary;
import data_access.FinnhubSearchDataAccessObject;
import interface_adapter.company_details.CompanyDetailsController;
import interface_adapter.company_details.CompanyDetailsViewModel;
import use_case.signup.SignupUserDataAccessInterface;
import view.*;

import interface_adapter.change_password.ChangePwdController;
import interface_adapter.change_password.ChangePwdViewModel;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    private final LoginUserDataAccessInterface userDataAccessObject = new DBUserDataAccessObject();
    private final DBPortfoliosDataAccessObject portfoliosDataAccessObject = new DBPortfoliosDataAccessObject();
    private final DBTransactionDataAccessObject transactionDataAccessObject = new DBTransactionDataAccessObject();
    private final DBStockDataAccessObject stockDataAccessObject = new DBStockDataAccessObject();

    private final MainViewModel mainViewModel = new MainViewModel();
    private final LoginViewModel loginViewModel = new LoginViewModel();
    private final SignupViewModel signupViewModel = new SignupViewModel();
    private final PortfolioHubViewModel portfoliosViewModel = new PortfolioHubViewModel();
    private final NewsViewModel newsViewModel = new NewsViewModel();
    private final CreateViewModel createViewModel = new CreateViewModel();
    private final PortfolioViewModel portfolioViewModel = new PortfolioViewModel();
    private final BuyViewModel buyViewModel = new BuyViewModel();
    private final SellViewModel sellViewModel = new SellViewModel();
    private final ShortViewModel shortViewModel = new ShortViewModel();
    private final HistoryViewModel historyViewModel = new HistoryViewModel();
    private final AnalysisViewModel analysisViewModel = new AnalysisViewModel();
    private final RecommendViewModel recommendViewModel = new RecommendViewModel();
    private final LeaderboardViewModel leaderboardViewModel = new LeaderboardViewModel();
    private final ChangePwdViewModel changePwdViewModel = new ChangePwdViewModel();
    private final NavigationState navigationState = new NavigationState();
    private final NavigationOutputBoundary navigationPresenter = new NavigationPresenter(viewManagerModel);

    private final NavigationInteractor navigationInteractor = new NavigationInteractor(navigationState,
            navigationPresenter);
    private final NavigationController navigationController = new NavigationController(navigationInteractor);

    private final LoginController loginController = LoginUseCaseFactory.create(
            viewManagerModel,
            mainViewModel,
            loginViewModel,
            signupViewModel,
            userDataAccessObject);
    private final SignupController signupController = SignupUseCaseFactory.create(
            viewManagerModel,
            signupViewModel,
            loginViewModel,
            (SignupUserDataAccessInterface) userDataAccessObject);
    private final PortfolioHubController portfolioHubController = PortfolioHubUseCaseFactory.create(
            viewManagerModel,
            portfoliosViewModel,
            createViewModel,
            portfoliosDataAccessObject);

    private final CreateController createController = CreateUseCaseFactory.create(
            viewManagerModel,
            portfoliosViewModel,
            createViewModel,
            portfoliosDataAccessObject);

    private SearchDataAccessInterface searchDataAccessObject;
    private NewsController newsController;
    private final PortfolioController portfolioController = PortfolioUseCaseFactory.create(
            viewManagerModel,
            portfolioViewModel,
            buyViewModel,
            sellViewModel,
            shortViewModel,
            transactionDataAccessObject,
            stockDataAccessObject,
            navigationController);

    private final BuyController buyController = BuyUseCaseFactory.create(
            viewManagerModel,
            buyViewModel,
            portfolioViewModel,
            stockDataAccessObject,
            transactionDataAccessObject);

    private final SellController sellController = SellUseCaseFactory.create(
            viewManagerModel,
            sellViewModel,
            portfolioViewModel,
            stockDataAccessObject,
            transactionDataAccessObject);
    // Short selling controller (initialized in constructor)
    private ShortController shortController;

    private final HistoryController historyController = HistoryUseCaseFactory.create(
            viewManagerModel,
            historyViewModel,
            transactionDataAccessObject,
            navigationController);

    private final AnalysisController analysisController = AnalysisUseCaseFactory.create(
            viewManagerModel,
            analysisViewModel,
            stockDataAccessObject,
            transactionDataAccessObject,
            navigationController);

    private final RecommendController recommendController = RecommendUseCaseFactory.create(
            viewManagerModel,
            recommendViewModel,
            stockDataAccessObject,
            transactionDataAccessObject);

    private final ChangePwdController changePwdController = ChangePwdUseCaseFactory.create(
            changePwdViewModel,
            userDataAccessObject,
            mainViewModel
    );

    private final SearchViewModel searchViewModel = new SearchViewModel();
    private SearchController searchController;

    private final interface_adapter.dashboard.DashboardViewModel dashboardViewModel = new interface_adapter.dashboard
            .DashboardViewModel();
    private interface_adapter.dashboard.DashboardController dashboardController;

    private final CompanyDetailsViewModel companyDetailsViewModel = new CompanyDetailsViewModel();
    private CompanyDetailsController companyDetailsController;

    private SettingsView settingsView;
    private TabbedMainView tabbedMainView;
    private DashboardView dashboardView;
    private WatchlistView watchlistView;
    private LeaderboardView leaderboardView;
    private LoginView loginView;
    private SignupView signupView;
    private PortfolioHubView portfoliosView;
    private CreateView createView;
    private PortfolioView portfolioView;
    private NewsView newsView;
    private BuyView buyView;
    private SellView sellView;
    private ShortView shortView;
    private HistoryView historyView;
    private AnalysisView analysisView;
    private RecommendView recommendView;
    private CompanyDetailsView companyDetailsView;

    public AppBuilder() throws SQLException, IOException {
        cardPanel.setLayout(cardLayout);
        SearchOutputBoundary searchPresenter = new SearchPresenter(searchViewModel);
        SearchDataAccessInterface tempSearchDataAccessObject;
        try {
            tempSearchDataAccessObject = new FinnhubSearchDataAccessObject();
        } catch (IOException exception) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Failed to initialize FinnHub search API. Application exiting...\n"
                            + exception.getMessage(),
                    "Initialization Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            tempSearchDataAccessObject = null;
        }
        this.searchDataAccessObject = tempSearchDataAccessObject;
        SearchInputBoundary getMatches = new GetMatches(searchDataAccessObject, searchPresenter);
        this.searchController = new SearchController(getMatches);

        this.newsController = NewsUseCaseFactory.create(
                viewManagerModel,
                newsViewModel,
                transactionDataAccessObject,
                searchDataAccessObject);

        // Initialize dashboard controller
        this.dashboardController = DashboardUseCaseFactory.createDashboardController(dashboardViewModel);
        this.companyDetailsController = CompanyDetailsUseCaseFactory.create(
                viewManagerModel, companyDetailsViewModel, navigationController);
    }

    /**
     * Adds the settings view to the application.
     *
     * @return this builder
     */
    public AppBuilder addSettingsView() {
        settingsView = new SettingsView(changePwdViewModel, viewManager, loginView, dashboardController,
                mainViewModel);
        settingsView.setController(changePwdController);
        cardPanel.add(settingsView, settingsView.getViewName());
        return this;
    }

    /**
     * Adds the Tabbed Main View to the application.
     *
     * @return this builder
     */
    public AppBuilder addTabbedMainView() throws IOException {
        dashboardView = DashboardViewFactory.create(mainViewModel, searchController, searchViewModel,
                dashboardViewModel, dashboardController, navigationController,
                companyDetailsController);
        final LeaderboardController tempLeaderboardController = LeaderboardUseCaseFactory
                .createLeaderboardController(leaderboardViewModel);
        this.companyDetailsController = CompanyDetailsUseCaseFactory.create(
                viewManagerModel, companyDetailsViewModel, navigationController);

        // Initialize short sell use case
        ShortOutputBoundary shortPresenter = new ShortPresenter(viewManagerModel, shortViewModel, portfolioViewModel);
        ShortInputBoundary shortInteractor = new ShortInteractor(
                (ShortStockDataAccessInterface) stockDataAccessObject,
                (ShortTransactionDataAccessInterface) transactionDataAccessObject,
                shortPresenter);
        this.shortController = new ShortController(shortInteractor);

        tabbedMainView = TabbedMainViewFactory.create(mainViewModel, portfolioHubController, newsController,
                portfolioController, navigationController, searchController, searchViewModel,
                dashboardView, portfoliosView, newsView, watchlistView, leaderboardView, settingsView);
        cardPanel.add(tabbedMainView, tabbedMainView.getViewName());
        return this;
    }

    /**
     * Adds the login view to the application.
     *
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginView = LoginViewFactory.create(loginViewModel, loginController);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the signup view to the application.
     *
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupView = SignupViewFactory.create(signupViewModel, signupController);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the portfolios view to the application.
     *
     * @return this builder
     */
    public AppBuilder addPortfoliosView() {
        portfoliosView = PortfolioHubViewFactory.create(
                portfoliosViewModel,
                portfolioHubController,
                portfolioController,
                navigationController);
        cardPanel.add(portfoliosView, portfoliosView.getViewName());
        return this;
    }

    /**
     * Adds the create view to the application.
     *
     * @return this builder
     */
    public AppBuilder addCreateView() {
        createView = CreateViewFactory.create(
                createViewModel,
                createController,
                viewManagerModel);
        cardPanel.add(createView, createView.getViewName());
        return this;
    }

    /**
     * Adds the portfolio view to the application.
     *
     * @return this builder
     */
    public AppBuilder addPortfolioView() {
        portfolioView = PortfolioViewFactory.create(
                portfolioViewModel,
                portfolioController,
                historyController,
                analysisController,
                recommendController,
                viewManagerModel);
        cardPanel.add(portfolioView, portfolioView.getViewName());
        return this;
    }

    /**
     * Adds the news view to the application.
     *
     * @return this builder
     */
    public AppBuilder addNewsView() {
        newsController = NewsUseCaseFactory.create(
                viewManagerModel,
                newsViewModel,
                transactionDataAccessObject,
                searchDataAccessObject
        );
        newsView = NewsViewFactory.create(newsViewModel, navigationController);
        newsView.setNewsController(newsController);
        cardPanel.add(newsView, newsView.getViewName());
        return this;
    }

    /**
     * Adds the buy view to the application.
     *
     * @return this builder
     */
    public AppBuilder addBuyView() {
        buyView = BuyViewFactory.create(
                buyViewModel,
                buyController,
                navigationController);
        cardPanel.add(buyView, buyView.getViewName());
        return this;
    }

    /**
     * Adds the sell view to the application.
     *
     * @return this builder
     */
    public AppBuilder addSellView() {
        sellView = SellViewFactory.create(sellViewModel, sellController, navigationController);
        cardPanel.add(sellView, sellView.getViewName());
        return this;
    }

    /**
     * Adds the short sell view to the application.
     *
     * @return this builder
     */
    public AppBuilder addShortView() {
        // Initialize short use case (only once)
        if (shortController == null) {
            ShortOutputBoundary shortPresenter =
                    new ShortPresenter(viewManagerModel, shortViewModel, portfolioViewModel);
            ShortInputBoundary shortInteractor = new ShortInteractor(
                    (ShortStockDataAccessInterface) stockDataAccessObject,
                    (ShortTransactionDataAccessInterface) transactionDataAccessObject,
                    shortPresenter);
            shortController = new ShortController(shortInteractor);
        }
        shortView = new ShortView(shortViewModel, shortController, navigationController);
        cardPanel.add(shortView, shortView.getViewName());
        return this;
    }

    /**
     * Adds the history view to the application.
     *
     * @return this builder
     */
    public AppBuilder addHistoryView() {
        historyView = HistoryViewFactory.create(historyViewModel, navigationController);
        cardPanel.add(historyView, historyView.getViewName());
        return this;
    }

    /**
     * Adds the analysis view to the application.
     *
     * @return this builder
     */
    public AppBuilder addAnalysisView() {
        analysisView = AnalysisViewFactory.create(
                analysisViewModel,
                navigationController);
        cardPanel.add(analysisView, analysisView.getViewName());
        return this;
    }

    /**
     * Adds the recommend view to the application.
     *
     * @return this builder
     */
    public AppBuilder addRecommendView() {
        recommendView = RecommendViewFactory.create(
                recommendViewModel,
                recommendController,
                viewManagerModel);
        cardPanel.add(recommendView, recommendView.getViewName());
        return this;
    }

    /**
     * Adds the company details view to the application.
     *
     * @return this builder
     */
    public AppBuilder addCompanyDetailsView() {
        companyDetailsView = CompanyDetailsViewFactory.create(
                companyDetailsViewModel,
                companyDetailsController,
                viewManagerModel);
        cardPanel.add(companyDetailsView, companyDetailsView.getViewName());
        return this;
    }

    /**
     * Adds the Leaderboard View to the application.
     *
     * @return this builder
     */
    public AppBuilder addLeaderboardView() {
        final LeaderboardController leaderboardController =
                LeaderboardUseCaseFactory.createLeaderboardController(leaderboardViewModel);
        leaderboardView = LeaderboardViewFactory.create(
                leaderboardViewModel,
                leaderboardController);
        cardPanel.add(leaderboardView, leaderboardView.getViewName());
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to
     * be displayed.
     *
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("FUNDI");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
        return application;
    }
}
