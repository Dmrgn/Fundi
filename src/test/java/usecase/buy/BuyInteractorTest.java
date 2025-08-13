package usecase.buy;

import dataaccess.DBTransactionDataAccessObject;
import dataaccess.DBStockDataAccessObject;
import entity.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BuyInteractorTest {
    @BeforeAll
    static void setUp() throws SQLException {
        DBTransactionDataAccessObject dbTransactionDataAccessObject = new DBTransactionDataAccessObject();
        dbTransactionDataAccessObject.save(new Transaction("51", "NVDA", 10,
                LocalDate.now(), 10.0));

        // Ensure portfolio "51" has sufficient balance for testing
        // NVDA price might be around $400-500, so 10 shares = $4000-5000
        // Set balance to $50,000 to ensure sufficient funds
        dbTransactionDataAccessObject.updatePortfolioBalance("51", 50000.0);
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
        BuyInteractor buyInteractor = new BuyInteractor(stockDataAccessInterface, transactionDataAccessInterface,
                buyPresenter);
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
        BuyInteractor buyInteractor = new BuyInteractor(stockDataAccessInterface, transactionDataAccessInterface,
                buyPresenter);
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
        BuyInteractor buyInteractor = new BuyInteractor(stockDataAccessInterface, transactionDataAccessInterface,
                buyPresenter);
        buyInteractor.execute(buyInputData);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBTransactionDataAccessObject db = new DBTransactionDataAccessObject();
        db.hardDeleteAllFor("51", "NVDA");
    }
}
