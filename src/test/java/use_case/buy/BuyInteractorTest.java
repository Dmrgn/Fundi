package use_case.buy;

import data_access.DBTransactionDataAccessObject;
import data_access.DBStockDataAccessObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BuyInteractorTest {
    @BeforeAll
    static void setUp() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessObject = new DBTransactionDataAccessObject();
        transactionDataAccessObject.remove("51", "NVDA", 10); // Update portfolio id accordingly
    }

    @Test
    void buySuccess() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        BuyStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject();

        String ticker = "NVDA";
        int amount = 10;
        String portfolioId = "51"; // Using test portfolio from create

        BuyInputData buyInputData = new BuyInputData(portfolioId, ticker, amount);
        BuyOutputBoundary buyPresenter = new BuyOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyOutputData outputData) {
                assertTrue(transactionDataAccessInterface.hasTransaction(portfolioId, ticker, amount));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should never get here");
            }
        };
        BuyInteractor buyInteractor = new BuyInteractor(stockDataAccessInterface, transactionDataAccessInterface, buyPresenter);
        buyInteractor.execute(buyInputData);
    }

    @Test
    void buyAmountFail() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        BuyStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject();

        String ticker = "NVDA";
        int amount = -1;
        String portfolioId = "51"; // Using test portfolio from create

        BuyInputData buyInputData = new BuyInputData(portfolioId, ticker, amount);
        BuyOutputBoundary buyPresenter = new BuyOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyOutputData outputData) {
                fail("Should never get here");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Amount must be greater than 0", errorMessage);
            }
        };
        BuyInteractor buyInteractor = new BuyInteractor(stockDataAccessInterface, transactionDataAccessInterface, buyPresenter);
        buyInteractor.execute(buyInputData);
    }

    @Test
    void buyTickerFail() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        BuyStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject();

        String ticker = "WRONG";
        int amount = 1;
        String portfolioId = "51"; // Using test portfolio from create

        BuyInputData buyInputData = new BuyInputData(portfolioId, ticker, amount);
        BuyOutputBoundary buyPresenter = new BuyOutputBoundary() {
            @Override
            public void prepareSuccessView(BuyOutputData outputData) {
                fail("Should never get here");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Ticker is not available", errorMessage);
            }
        };
        BuyInteractor buyInteractor = new BuyInteractor(stockDataAccessInterface, transactionDataAccessInterface, buyPresenter);
        buyInteractor.execute(buyInputData);
    }
}
