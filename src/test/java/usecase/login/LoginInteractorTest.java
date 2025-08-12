package usecase.login;

import dataaccess.DBUserDataAccessObject;
import entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {
    @BeforeAll
    static void setUp() throws SQLException {
        DBUserDataAccessObject userRepository = new DBUserDataAccessObject();
        userRepository.save(new User("Paul", "password"));
    }

    @Test
    void successTest() throws SQLException {
        DBUserDataAccessObject userRepository = new DBUserDataAccessObject();
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignupView() {
                // Not testing this method
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() throws SQLException {
        LoginInputData inputData = new LoginInputData("Paul", "wrong");
        LoginUserDataAccessInterface userRepository = new DBUserDataAccessObject();

        // For this failure test, we need to add Paul to the data access repository before we log in, and
        // the passwords should not match.

        // This creates a presenter that tests whether the test case is as we expect.
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for Paul.", error);
            }

            @Override
            public void switchToSignupView() {
                // Not testing this
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() throws SQLException {
        LoginInputData inputData = new LoginInputData("newPaul", "password");
        LoginUserDataAccessInterface userRepository = new DBUserDataAccessObject();

        // Add Paul to the repo so that when we check later they already exist

        // This creates a presenter that tests whether the test case is as we expect.
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("newPaul: Account does not exist.", error);
            }

            @Override
            public void switchToSignupView() {
                // Not testing this
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBUserDataAccessObject userRepository = new DBUserDataAccessObject();
        userRepository.remove("Paul");
    }
}