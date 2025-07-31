package use_case.sell;

import data_access.DBTransactionDataAccessObject;
import data_access.DBStockDataAccessObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SellInteractorTest {
    @Test
    void sellSuccess() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        SellStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject();

        String ticker = "NVDA";
        int amount = 1;
        String portfolioId = "51"; // Using test portfolio from create

        SellInputData sellInputData = new SellInputData(portfolioId, ticker, amount);
        SellOutputBoundary sellPresenter = new SellOutputBoundary() {
            @Override
            public void prepareSuccessView(SellOutputData outputData) {
                assertTrue(transactionDataAccessInterface.hasTransaction(portfolioId, ticker, amount));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should never get here");
            }
        };
        SellInteractor sellInteractor = new SellInteractor(stockDataAccessInterface, transactionDataAccessInterface, sellPresenter);
        sellInteractor.execute(sellInputData);
    }

    @Test
    void sellAmountFail() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        SellStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject();

        String ticker = "NVDA";
        int amount = -1;
        String portfolioId = "51"; // Using test portfolio from create

        SellInputData buyInputData = new SellInputData(portfolioId, ticker, amount);
        SellOutputBoundary sellPresenter = new SellOutputBoundary() {
            @Override
            public void prepareSuccessView(SellOutputData outputData) {
                fail("Should never get here");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Invalid amount", errorMessage);
            }
        };
        SellInteractor sellInteractor = new SellInteractor(stockDataAccessInterface, transactionDataAccessInterface, sellPresenter);
        sellInteractor.execute(buyInputData);
    }

    @Test
    void sellTooMuchAmountFail() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        SellStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject();

        String ticker = "NVDA";
        int amount = 11;
        String portfolioId = "51"; // Using test portfolio from create

        SellInputData buyInputData = new SellInputData(portfolioId, ticker, amount);
        SellOutputBoundary sellPresenter = new SellOutputBoundary() {
            @Override
            public void prepareSuccessView(SellOutputData outputData) {
                fail("Should never get here");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You do not have enough of this ticker", errorMessage);
            }
        };
        SellInteractor sellInteractor = new SellInteractor(stockDataAccessInterface, transactionDataAccessInterface, sellPresenter);
        sellInteractor.execute(buyInputData);
    }

    @Test
    void sellTickerFail() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        SellStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject();

        String ticker = "wrong";
        int amount = 5;
        String portfolioId = "51"; // Using test portfolio from create

        SellInputData buyInputData = new SellInputData(portfolioId, ticker, amount);
        SellOutputBoundary sellPresenter = new SellOutputBoundary() {
            @Override
            public void prepareSuccessView(SellOutputData outputData) {
                fail("Should never get here");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Invalid ticker", errorMessage);
            }
        };
        SellInteractor sellInteractor = new SellInteractor(stockDataAccessInterface, transactionDataAccessInterface, sellPresenter);
        sellInteractor.execute(buyInputData);
    }
}
