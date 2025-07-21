package interface_adapter.main;

import use_case.main.*;

import java.util.Map;

/**
 * The Login Interactor.
 */
public class MainInteractor implements MainInputBoundary {
    private final MainDataAccessInterface mainDataAccessObject;
    private final MainOutputBoundary mainPresenter;

    public MainInteractor(MainDataAccessInterface mainDataAccessObject,
                           MainOutputBoundary mainPresenter) {
        this.mainDataAccessObject = mainDataAccessObject;
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void execute(MainInputData mainInputData) {
        final String username = mainInputData.getUsername();
        final String useCase = mainInputData.getUseCase();
        final Map<String, String> portfolios = mainDataAccessObject.getPortfolios(username);
        mainPresenter.prepareView(new MainOutputData(username, useCase, portfolios));

    }
}