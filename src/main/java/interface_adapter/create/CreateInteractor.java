package interface_adapter.create;

import use_case.create.*;
import use_case.login.LoginOutputBoundary;

/**
 * The Login Interactor.
 */
public class CreateInteractor implements CreateInputBoundary {
    private final CreateDataAccessInterface createDataAccessObject;
    private final CreateOutputBoundary createPresenter;

    public CreateInteractor(CreateDataAccessInterface createDataAccessObject,
                            CreateOutputBoundary createOutputBoundary) {
        this.createDataAccessObject = createDataAccessObject;
        this.createPresenter = createOutputBoundary;
    }

    @Override
    public void execute(CreateInputData createInputData) {
        final String username = createInputData.getUsername();
        final String portfolioName = createInputData.getPortfolioName();
        if (createDataAccessObject.existsByName(portfolioName, username)) {
            createPresenter.prepareFailView("You already have a portfolio with this name");
        } else {
                createDataAccessObject.save(portfolioName, username);
                CreateOutputData createOutputData = new CreateOutputData(
                        username,createDataAccessObject.getPortfolios(username));
                createPresenter.prepareSuccessView(createOutputData);
        }
    }
}