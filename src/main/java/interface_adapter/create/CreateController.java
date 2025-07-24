package interface_adapter.create;

import use_case.create.CreateInputBoundary;
import use_case.create.CreateInputData;

/**
 * The controller for the Create Use Case.
 */
public class CreateController {

    private final CreateInputBoundary createUseCaseInteractor;

    public CreateController(CreateInputBoundary createUseCaseInteractor) {
        this.createUseCaseInteractor = createUseCaseInteractor;
    }

    /**
     * Executes the create Use Case.
     * @param username the username of the user logging in
     * @param portfolioName the new portfolio name
     */
    public void execute(String username, String portfolioName) {
        final CreateInputData createInputData = new CreateInputData(username, portfolioName);

        createUseCaseInteractor.execute(createInputData);
    }
}