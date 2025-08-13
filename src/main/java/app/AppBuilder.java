package app;

import java.awt.CardLayout;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import dataaccess.DBPortfoliosDataAccessObject;
import dataaccess.DBStockDataAccessObject;
import dataaccess.DBTransactionDataAccessObject;
import dataaccess.DBUserDataAccessObject;
import dataaccess.FinnhubSearchDataAccessObject;
import entity.NavigationState;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.analysis.AnalysisController;
import interfaceadapter.analysis.AnalysisViewModel;
import interfaceadapter.buy.BuyController;
import interfaceadapter.buy.BuyViewModel;
import interfaceadapter.change_password.ChangePwdController;
import interfaceadapter.change_password.ChangePwdViewModel;
import interfaceadapter.company_details.CompanyDetailsController;
import interfaceadapter.company_details.CompanyDetailsViewModel;
import interfaceadapter.create.CreateController;
import interfaceadapter.create.CreateViewModel;
import interfaceadapter.history.HistoryController;
import interfaceadapter.history.HistoryViewModel;
import interfaceadapter.leaderboard.LeaderboardController;
import interfaceadapter.leaderboard.LeaderboardViewModel;
import interfaceadapter.login.LoginController;
import interfaceadapter.login.LoginViewModel;
import interfaceadapter.main.MainViewModel;
import interfaceadapter.navigation.NavigationController;
import interfaceadapter.navigation.NavigationPresenter;
import interfaceadapter.news.NewsController;
import interfaceadapter.news.NewsViewModel;
import interfaceadapter.portfolio.PortfolioController;
import interfaceadapter.portfolio.PortfolioViewModel;
import interfaceadapter.portfolio.DeletePortfolioController;
import interfaceadapter.portfolio_hub.PortfolioHubController;
import interfaceadapter.portfolio_hub.PortfolioHubViewModel;
import interfaceadapter.recommend.RecommendController;
import interfaceadapter.recommend.RecommendViewModel;
import interfaceadapter.search.SearchController;
import interfaceadapter.search.SearchPresenter;
import interfaceadapter.search.SearchViewModel;
import interfaceadapter.sell.SellController;
import interfaceadapter.sell.SellViewModel;
import interfaceadapter.signup.SignupController;
import interfaceadapter.signup.SignupViewModel;
import usecase.change_password.ChangePwdInputBoundary;
import usecase.change_password.ChangePwdInteractor;
import usecase.change_password.ChangePwdOutputBoundary;
import usecase.change_password.ChangePwdPresenter;
import usecase.login.LoginUserDataAccessInterface;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginInputBoundary;
import usecase.login.LoginInteractor;
import interfaceadapter.login.LoginPresenter;
import usecase.navigation.NavigationInteractor;
import usecase.navigation.NavigationOutputBoundary;
import usecase.remember_me.RememberMeInteractor;
import usecase.remember_me.RememberMeUserDataAccessInterface;
import usecase.search.GetMatches;
import usecase.search.SearchDataAccessInterface;
import usecase.search.SearchInputBoundary;
import usecase.search.SearchOutputBoundary;
import usecase.signup.SignupUserDataAccessInterface;
import view.AnalysisView;
import view.BuyView;
import view.CompanyDetailsView;
import view.CreateView;
import view.DashboardView;
import view.HistoryView;
import view.LeaderboardView;
import view.LoginView;
import view.NewsView;
import view.PortfolioHubView;
import view.PortfolioView;
import view.RecommendView;
import view.SellView;
import view.SettingsView;
import view.SignupView;
import view.TabbedMainView;
import view.ViewManager;
import view.WatchlistView;

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
    private final HistoryViewModel historyViewModel = new HistoryViewModel();
    private final AnalysisViewModel analysisViewModel = new AnalysisViewModel();
    private final RecommendViewModel recommendViewModel = new RecommendViewModel();
    private final LeaderboardViewModel leaderboardViewModel = new LeaderboardViewModel();
    private final NavigationState navigationState = new NavigationState();
    private final NavigationOutputBoundary navigationPresenter = new NavigationPresenter(viewManagerModel);

    private final NavigationInteractor navigationInteractor = new NavigationInteractor(navigationState,
            navigationPresenter);
    private final NavigationController navigationController = new NavigationController(navigationInteractor);

    private LoginController loginController;
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

    private final SearchViewModel searchViewModel = new SearchViewModel();
    private SearchController searchController;

    private final interfaceadapter.dashboard.DashboardViewModel dashboardViewModel = new interfaceadapter.dashboard.DashboardViewModel();
    private interfaceadapter.dashboard.DashboardController dashboardController;

    private final CompanyDetailsViewModel companyDetailsViewModel = new CompanyDetailsViewModel();
    private CompanyDetailsController companyDetailsController;
    private final ChangePwdViewModel changePwdViewModel = new ChangePwdViewModel();

    private SettingsView settingsView;
    private ChangePwdController changePwdController;

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
    private HistoryView historyView;
    private AnalysisView analysisView;
    private RecommendView recommendView;
    private CompanyDetailsView companyDetailsView;

    private RememberMeInteractor rememberMeInteractor;

    public AppBuilder() throws SQLException, IOException {
        cardPanel.setLayout(cardLayout);
        SearchOutputBoundary searchPresenter = new SearchPresenter(searchViewModel);
        SearchDataAccessInterface tempSearchDataAccessObject;
        try {
            tempSearchDataAccessObject = new FinnhubSearchDataAccessObject();
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Failed to initialize FinnHub search API. Application exiting...\n"
                            + ex.getMessage(),
                    "Initialization Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return;
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
     * Registers and wires the Change Password use case.
     *
     * @return this builder
     */
    public AppBuilder addChangePwdUseCase() {
        ChangePwdOutputBoundary presenter = new ChangePwdPresenter(changePwdViewModel);
        ChangePwdInputBoundary interactor = new ChangePwdInteractor(userDataAccessObject, presenter,
                mainViewModel);
        changePwdController = new ChangePwdController(interactor, mainViewModel);
        return this;
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
        settingsView.setLoginController(loginController);
        cardPanel.add(settingsView, settingsView.getViewName());
        return this;
    }

    /**
     * Adds the Tabbed Main View to the application.
     *
     * @return this builder
     */
    public AppBuilder addTabbedMainView() {
        dashboardView = DashboardViewFactory.create(mainViewModel, searchController, searchViewModel,
                dashboardViewModel, dashboardController, navigationController,
                companyDetailsController);
        final LeaderboardController tempLeaderboardController = LeaderboardUseCaseFactory
                .createLeaderboardController(leaderboardViewModel);
        leaderboardView = LeaderboardViewFactory.create(leaderboardViewModel, tempLeaderboardController);
        watchlistView = WatchlistViewFactory.create(mainViewModel,
                (DBUserDataAccessObject) userDataAccessObject);
        tabbedMainView = TabbedMainViewFactory.create(mainViewModel, portfolioHubController,
                dashboardController, newsController,
                portfolioController, navigationController, searchController, searchViewModel,
                dashboardView, portfoliosView, newsView, watchlistView, leaderboardView, settingsView);
        cardPanel.add(tabbedMainView, tabbedMainView.getViewName());
        return this;
    }

    /**
     * Adds the log in view to the application.
     *
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginView = LoginViewFactory.create(loginViewModel, loginController);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the sign-up view to the application.
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
        // Wire Delete Portfolio use case controller
        DeletePortfolioController deletePortfolioController = DeletePortfolioUseCaseFactory.create(
                portfolioHubController,
                portfoliosDataAccessObject);
        portfolioView = PortfolioViewFactory.create(
                portfolioViewModel,
                portfolioController,
                historyController,
                analysisController,
                recommendController,
                viewManagerModel,
                deletePortfolioController);
        cardPanel.add(portfolioView, portfolioView.getViewName());
        return this;
    }

    /**
     * Adds the news view to the application.
     *
     * @return this builder
     */
    public AppBuilder addNewsView() {
        // You already have a searchDAO instance, reuse it.
        // If not, create one: SearchDataAccessInterface searchDAO = new
        // FinnhubSearchDataAccessObject();
        newsController = NewsUseCaseFactory.create(
                viewManagerModel,
                newsViewModel,
                transactionDataAccessObject,
                searchDataAccessObject);
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
     * Adds the history view to the application.
     *
     * @return this builder
     */
    public AppBuilder addHistoryView() {

        historyView = HistoryViewFactory.create(
                historyViewModel,
                navigationController);

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
     * Adds the recommendations view to the application.
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
     * Adds the Company details view to the application.
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

        // Trigger auto-login after all views are registered
        if (rememberMeInteractor != null) {
            rememberMeInteractor.checkRememberMe();
        }

        return application;
    }

    /**
     * Adds the Leaderboard View to the application.
     *
     * @return this builder
     */
    public AppBuilder addLeaderboardView() {
        final LeaderboardController leaderboardController = LeaderboardUseCaseFactory
                .createLeaderboardController(leaderboardViewModel);
        leaderboardView = LeaderboardViewFactory.create(
                leaderboardViewModel,
                leaderboardController);
        cardPanel.add(leaderboardView, leaderboardView.getViewName());
        return this;
    }

    /**
     * Adds the login use case, including Remember Me functionality.
     *
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        // Build the login presenter and interactor
        LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel, mainViewModel, loginViewModel,
                signupViewModel);
        LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);

        // Remember Me functionality
        this.rememberMeInteractor = new RememberMeInteractor(
                (RememberMeUserDataAccessInterface) userDataAccessObject, loginInteractor);
        loginController = new LoginController(loginInteractor, rememberMeInteractor);

        return this;
    }
}
