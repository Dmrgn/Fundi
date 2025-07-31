package use_case.create;

import data_access.DBPortfoliosDataAccessObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CreateInteractorTest {
    @Test
    void createSuccessTest() throws SQLException {
        CreateDataAccessInterface createDataAccessInterface = new DBPortfoliosDataAccessObject();
        String portfolioName = "testPortfolio";
        String username = "Paul";

        CreateInputData createInputData = new CreateInputData(username, portfolioName);
        CreateOutputBoundary createOutputBoundary = new CreateOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateOutputData outputData) {
                assertTrue(createDataAccessInterface.existsByName(portfolioName, username));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Did not save portfolio correctly");
            }
        };
        CreateInteractor createInteractor = new CreateInteractor(createDataAccessInterface, createOutputBoundary);
        createInteractor.execute(createInputData);
    }

    @Test
    void createDuplicateTest() throws SQLException {
        CreateDataAccessInterface createDataAccessInterface = new DBPortfoliosDataAccessObject();
        String portfolioName = "testPortfolio";
        String username = "Paul";

        CreateInputData createInputData = new CreateInputData(username, portfolioName);
        CreateOutputBoundary createOutputBoundary = new CreateOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateOutputData outputData) {
                fail("Saved duplicate portfolio");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(createDataAccessInterface.existsByName(portfolioName, username));
            }
        };
        CreateInteractor createInteractor = new CreateInteractor(createDataAccessInterface, createOutputBoundary);
        createInteractor.execute(createInputData);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBPortfoliosDataAccessObject portfoliosDataAccessObject = new DBPortfoliosDataAccessObject();
        if (portfoliosDataAccessObject.existsByName("testPortfolio", "Paul")) {
            portfoliosDataAccessObject.remove("testPortfolio", "Paul");
        }
    }
}
