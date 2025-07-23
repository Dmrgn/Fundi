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
import interface_adapter.ViewManagerModel;
import interface_adapter.buy.BuyController;
import interface_adapter.buy.BuyInteractor;
import interface_adapter.buy.BuyPresenter;
import interface_adapter.buy.BuyViewModel;
import interface_adapter.create.CreateController;
import interface_adapter.create.CreateInteractor;
import interface_adapter.create.CreatePresenter;
import interface_adapter.create.CreateViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioInteractor;
import interface_adapter.portfolio.PortfolioPresenter;
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.signup.SignupInteractor;
import interface_adapter.main.MainViewModel;
import interface_adapter.portfolios.*;
import use_case.buy.BuyInputBoundary;
import use_case.buy.BuyOutputBoundary;
import use_case.create.CreateInputBoundary;
import use_case.create.CreateOutputBoundary;
import use_case.login.LoginInputBoundary;
import interface_adapter.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginUserDataAccessInterface;
import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioOutputBoundary;
import use_case.portfolios.PortfoliosInputBoundary;
import use_case.portfolios.PortfoliosOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupOutputBoundary;
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
    private final LoginUserDataAccessInterface userDataAccessObject =
            new DBUserDataAccessObject(userFactory);
    private final DBPortfoliosDataAccessObject portfoliosDataAccessObject = new DBPortfoliosDataAccessObject();
    private final DBTransactionDataAccessObject transactionDataAccessObject = new DBTransactionDataAccessObject();
    private final DBStockDataAccessObject stockDataAccessObject = new DBStockDataAccessObject();

    private PortfolioController portfolioController;

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private MainViewModel mainViewModel;
    private PortfoliosViewModel portfoliosViewModel;
    private CreateViewModel createViewModel;
    private PortfolioViewModel portfolioViewModel;
    private BuyViewModel buyViewModel;
    private MainView mainView;
    private LoginView loginView;
    private PortfoliosView portfoliosView;
    private CreateView createView;
    private PortfolioView portfolioView;
    private BuyView buyView;

    public AppBuilder() throws SQLException {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Main View to the application.
     * @return this builder
     */
    public AppBuilder addMainView() {
        mainViewModel = new MainViewModel();
        mainView = new MainView(mainViewModel);
        cardPanel.add(mainView, mainView.getViewName());
        return this;
    }

    /**
     * Adds the portfolios view to the application
     * @return this builder
     */
    public AppBuilder addPortfoliosView() {
        portfoliosViewModel = new PortfoliosViewModel();
        portfoliosView = new PortfoliosView(portfoliosViewModel);
        cardPanel.add(portfoliosView, portfoliosView.getViewName());
        return this;
    }

    /**
     * Adds the create view to the application
     * @return this builder
     */
    public AppBuilder addCreateView() {
        createViewModel = new CreateViewModel();
        createView = new CreateView(createViewModel);
        cardPanel.add(createView, createView.getViewName());
        return this;
    }

    /**
     * Adds the portfolio view to the application
     * @return this builder
     */
    public AppBuilder addPortfolioView() {
        portfolioViewModel = new PortfolioViewModel();
        portfolioView = new PortfolioView(portfolioViewModel);
        cardPanel.add(portfolioView, portfolioView.getViewName());
        return this;
    }

    /**
     * Adds the buy view to the application
     * @return this builder
     */
    public AppBuilder addBuyView() {
        buyViewModel = new BuyViewModel();
        buyView = new BuyView(buyViewModel);
        cardPanel.add(buyView, buyView.getViewName());
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                mainViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }


    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                (SignupUserDataAccessInterface) userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }


    public AppBuilder addPortfoliosUseCase() {
        final PortfoliosOutputBoundary portfoliosOutputBoundary = new PortfoliosPresenter(viewManagerModel,
                portfoliosViewModel, createViewModel);
        final PortfoliosInputBoundary portfoliosInteractor = new PortfoliosInteractor(portfoliosOutputBoundary,
                portfoliosDataAccessObject);
        final PortfoliosController portfoliosController = new PortfoliosController(portfoliosInteractor);
        mainView.setController(portfoliosController);
        portfoliosView.setPortfoliosController(portfoliosController);
        return this;

    }

    public AppBuilder addCreateUseCase() {
        final CreateOutputBoundary createOutputBoundary = new CreatePresenter(viewManagerModel, portfoliosViewModel, createViewModel);
        final CreateInputBoundary createInteractor = new CreateInteractor(portfoliosDataAccessObject, createOutputBoundary);
        final CreateController createController = new CreateController(createInteractor);
        createView.setController(createController);
        return this;
    }

    public AppBuilder addPortfolioUseCase() {
        final PortfolioOutputBoundary portfolioOutputBoundary = new PortfolioPresenter(viewManagerModel, portfolioViewModel, buyViewModel);
        final PortfolioInputBoundary portfolioInputBoundary = new PortfolioInteractor(transactionDataAccessObject, portfolioOutputBoundary);

        portfolioController = new PortfolioController(portfolioInputBoundary);
        portfoliosView.setPortfolioController(portfolioController);
        portfolioView.setPortfolioController(portfolioController);
        return this;
    }

    public AppBuilder addBuyUseCase() {
        final BuyOutputBoundary buyOutputBoundary = new BuyPresenter(buyViewModel, portfolioController, portfolioViewModel.getState());
        final BuyInputBoundary buyInputBoundary = new BuyInteractor(stockDataAccessObject, transactionDataAccessObject,
                buyOutputBoundary);
        final BuyController buyController = new BuyController(buyInputBoundary);
        buyView.setBuyController(buyController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("FUNDI");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}