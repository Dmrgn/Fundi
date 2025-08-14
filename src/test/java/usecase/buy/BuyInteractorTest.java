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
        // Ensure database schema is initialized before any DAO operations
        dataaccess.DatabaseInitializer.ensureInitialized();

        // Create baseline user and portfolio rows if they don't exist
        try (java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite")) {
            try (java.sql.PreparedStatement ps = conn.prepareStatement(
                    "INSERT OR IGNORE INTO users (id, username, password) VALUES (1, 'testuser', 'testpass')")) {
                ps.executeUpdate();
            }
            try (java.sql.PreparedStatement ps = conn.prepareStatement(
                    "INSERT OR IGNORE INTO portfolios (id, name, user_id, balance) VALUES (51, 'Test Portfolio', 1, 10000)")) {
                ps.executeUpdate();
            }
        }

        DBTransactionDataAccessObject dbTransactionDataAccessObject = new DBTransactionDataAccessObject();
        // Seed a known existing transaction and ensure clean state
        dbTransactionDataAccessObject.hardDeleteAllFor("51", "NVDA");
        dbTransactionDataAccessObject.save(new Transaction("51", "NVDA", 10,
                LocalDate.now(), 10.0));

        // Ensure portfolio "51" has sufficient balance for testing
        dbTransactionDataAccessObject.updatePortfolioBalance("51", 50000.0);
    }

    @Test
    void buySuccess() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        BuyStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject(true); // Skip API calls

        String ticker = "NVDA";
        int amount = 10;
        String portfolioId = "51";

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
        BuyStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject(true); // Skip API calls

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
        BuyStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject(true); // Skip API calls

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
