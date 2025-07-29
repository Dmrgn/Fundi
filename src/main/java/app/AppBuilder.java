package app;

import java.awt.CardLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.DBPortfoliosDataAccessObject;
import data_access.DBStockDataAccessObject;
import data_access.DBTransactionDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import data_access.DBUserDataAccessObject;
import interface_adapter.PortfolioViewModelUpdater;
import interface_adapter.ViewManagerModel;
import interface_adapter.analysis.AnalysisController;
import interface_adapter.analysis.AnalysisViewModel;
import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.create.CreateController;
import interface_adapter.create.CreateViewModel;
import interface_adapter.history.HistoryController;
import interface_adapter.history.HistoryViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.recommend.RecommendController;
import interface_adapter.recommend.RecommendViewModel;
import interface_adapter.sell.SellController;
import interface_adapter.sell.SellViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.main.MainViewModel;
import interface_adapter.news.NewsController;
import use_case.news.NewsInteractor;
import interface_adapter.news.NewsPresenter;
import interface_adapter.news.NewsViewModel;
import interface_adapter.portfolios.*;
import use_case.login.LoginUserDataAccessInterface;
import use_case.news.NewsInputBoundary;
import use_case.news.NewsOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    private final LoginUserDataAccessInterface userDataAccessObject = new DBUserDataAccessObject(userFactory);
    private final DBPortfoliosDataAccessObject portfoliosDataAccessObject = new DBPortfoliosDataAccessObject();
    private final DBTransactionDataAccessObject transactionDataAccessObject = new DBTransactionDataAccessObject();
    private final DBStockDataAccessObject stockDataAccessObject = new DBStockDataAccessObject();

    private final MainViewModel mainViewModel = new MainViewModel();
    private final LoginViewModel loginViewModel = new LoginViewModel();
    private final SignupViewModel signupViewModel = new SignupViewModel();
    private final PortfoliosViewModel portfoliosViewModel = new PortfoliosViewModel();
    private final NewsViewModel newsViewModel = new NewsViewModel();
    private final CreateViewModel createViewModel = new CreateViewModel();
    private final PortfolioViewModel portfolioViewModel = new PortfolioViewModel();
    private final BuyViewModel buyViewModel = new BuyViewModel();
    private final SellViewModel sellViewModel = new SellViewModel();
    private final HistoryViewModel historyViewModel = new HistoryViewModel();
    private final AnalysisViewModel analysisViewModel = new AnalysisViewModel();
    private final RecommendViewModel recommendViewModel = new RecommendViewModel();
    private final PortfolioViewModelUpdater portfolioViewModelUpdater = new PortfolioViewModelUpdater();

    private final LoginController loginController = LoginUseCaseFactory.create(
            viewManagerModel,
            mainViewModel,
            loginViewModel,
            signupViewModel,
            userDataAccessObject
    );
    private final SignupController signupController = SignupUseCaseFactory.create(
            viewManagerModel,
            signupViewModel,
            loginViewModel,
            (SignupUserDataAccessInterface) userDataAccessObject
    );
    private final PortfoliosController portfoliosController = PortfoliosUseCaseFactory.create(
            viewManagerModel,
            portfoliosViewModel,
            createViewModel,
            portfoliosDataAccessObject
    );

    private final CreateController createController = CreateUseCaseFactory.create(
            viewManagerModel,
            portfoliosViewModel,
            createViewModel,
            portfoliosDataAccessObject
    );

    private final NewsController newsController = NewsUseCaseFactory.create(
            viewManagerModel,
            newsViewModel,
            transactionDataAccessObject
    );
    private final PortfolioController portfolioController = PortfolioUseCaseFactory.create(
            viewManagerModel,
            portfolioViewModel,
            buyViewModel,
            sellViewModel,
            transactionDataAccessObject,
            stockDataAccessObject
    );

    private final BuyController buyController = BuyUseCaseFactory.create(
            viewManagerModel,
            buyViewModel,
            portfolioViewModelUpdater,
            portfolioViewModel,
            stockDataAccessObject,
            transactionDataAccessObject
    );

    private final SellController sellController = SellUseCaseFactory.create(
            viewManagerModel,
            sellViewModel,
            portfolioViewModel,
            portfolioViewModelUpdater,
            stockDataAccessObject,
            transactionDataAccessObject
    );

    private final HistoryController historyController = HistoryUseCaseFactory.create(
            viewManagerModel,
            historyViewModel,
            transactionDataAccessObject
    );

    private final AnalysisController analysisController = AnalysisUseCaseFactory.create(
            viewManagerModel,
            analysisViewModel,
            stockDataAccessObject,
            transactionDataAccessObject
    );

    private final RecommendController recommendController = RecommendUseCaseFactory.create(
            viewManagerModel,
            recommendViewModel,
            stockDataAccessObject
    );

    private MainView mainView;
    private LoginView loginView;
    private SignupView signupView;
    private PortfoliosView portfoliosView;
    private CreateView createView;
    private PortfolioView portfolioView;
    private NewsView newsView;
    private BuyView buyView;
    private SellView sellView;
    private HistoryView historyView;
    private AnalysisView analysisView;
    private RecommendView recommendView;

    public AppBuilder() throws SQLException {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Main View to the application.
     * 
     * @return this builder
     */
    public AppBuilder addMainView() {
        mainView = MainViewFactory.create(mainViewModel, portfoliosController, newsController);
        cardPanel.add(mainView, mainView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginView = LoginViewFactory.create(loginViewModel, loginController);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addSignupView() {
        signupView = SignupViewFactory.create(signupViewModel, signupController);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the portfolios view to the application
     * 
     * @return this builder
     */
    public AppBuilder addPortfoliosView() {
        portfoliosView = PortfoliosViewFactory.create(
                portfoliosViewModel,
                portfoliosController,
                portfolioController
        );
        cardPanel.add(portfoliosView, portfoliosView.getViewName());
        return this;
    }

    /**
     * Adds the create view to the application
     * 
     * @return this builder
     */
    public AppBuilder addCreateView() {
        createView = CreateViewFactory.create(
                createViewModel,
                createController
        );
        cardPanel.add(createView, createView.getViewName());
        return this;
    }

    /**
     * Adds the portfolio view to the application
     * 
     * @return this builder
     */
    public AppBuilder addPortfolioView() {
        portfolioView = PortfolioViewFactory.create(
                portfolioViewModel,
                portfolioController,
                historyController,
                analysisController,
                recommendController
        );
        cardPanel.add(portfolioView, portfolioView.getViewName());
        return this;
    }

    public AppBuilder addNewsView() {
        newsView = new NewsView(newsViewModel);
        cardPanel.add(newsView, newsView.getViewName());
        return this;
    }

    /**
     * Adds the buy view to the application
     * 
     * @return this builder
     */
    public AppBuilder addBuyView() {
        buyView = BuyViewFactory.create(
                buyViewModel,
                buyController
        );
        cardPanel.add(buyView, buyView.getViewName());
        return this;
    }

    public AppBuilder addSellView() {
        sellView = SellViewFactory.create(sellViewModel, sellController);
        cardPanel.add(sellView, sellView.getViewName());
        return this;
    }

    public AppBuilder addHistoryView() {
        historyView = HistoryViewFactory.create(
                historyViewModel,
                historyController
        );
        cardPanel.add(historyView, historyView.getViewName());
        return this;
    }

    public AppBuilder addAnalysisView() {
        analysisView = AnalysisViewFactory.create(
                analysisViewModel,
                analysisController
        );
        cardPanel.add(analysisView, analysisView.getViewName());
        return this;
    }

    public AppBuilder addRecommendView() {
        recommendView = RecommendViewFactory.create(
                recommendViewModel,
                recommendController
        );
        cardPanel.add(recommendView, recommendView.getViewName());
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

    /**
     * Adds the Settings View to the application.
     * 
     * @return this builder
     */
    public AppBuilder addSettingsView() {
        SettingsView settingsView = new SettingsView(viewManager);
        cardPanel.add(settingsView, "settings");
        return this;
    }

}
