package use_case.create;

import data_access.DBPortfoliosDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CreateInteractorTest {
    @BeforeAll
    static void setUp() throws SQLException {
        // Assuming Login Test was already run

        DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject();
        userDataAccessObject.save(new User("Paul", "Paul"));
    }

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
        portfoliosDataAccessObject.remove("testPortfolio", "Paul");
        DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject();
        userDataAccessObject.remove("Paul");

    }
}
