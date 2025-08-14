package usecase.sell;

import dataaccess.DBTransactionDataAccessObject;
import dataaccess.DBStockDataAccessObject;
import entity.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SellInteractorTest {
    @BeforeAll
    static void setUp() throws SQLException {
        // Ensure database schema is initialized before any DAO operations
        dataaccess.DatabaseInitializer.ensureInitialized();
        
        // Create test portfolio 51 if it doesn't exist
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

        DBTransactionDataAccessObject db = new DBTransactionDataAccessObject();
        // Neutralize any leftover rows from other suites
        db.hardDeleteAllFor("51", "NVDA");
        // Clean up holdings table
        try (java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
                java.sql.PreparedStatement ps = conn
                        .prepareStatement("DELETE FROM holdings WHERE portfolio_id = 51 AND ticker = 'NVDA'")) {
            ps.executeUpdate();
        }
        // Seed with a clean long position
        db.save(new Transaction("51", "NVDA", 10, LocalDate.now(), 10.0));
    }

    @Test
    void sellSuccess() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        SellStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject(true); // Skip API calls

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
        SellInteractor sellInteractor = new SellInteractor(stockDataAccessInterface, transactionDataAccessInterface,
                sellPresenter);
        sellInteractor.execute(sellInputData);
    }

    @Test
    void sellAmountFail() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        SellStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject(true); // Skip API calls

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
        SellInteractor sellInteractor = new SellInteractor(stockDataAccessInterface, transactionDataAccessInterface,
                sellPresenter);
        sellInteractor.execute(buyInputData);
    }

    @Test
    void sellTooMuchAmountFail() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        SellStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject(true); // Skip API calls

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
        SellInteractor sellInteractor = new SellInteractor(stockDataAccessInterface, transactionDataAccessInterface,
                sellPresenter);
        sellInteractor.execute(buyInputData);
    }

    @Test
    void sellTickerFail() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessInterface = new DBTransactionDataAccessObject();
        SellStockDataAccessInterface stockDataAccessInterface = new DBStockDataAccessObject(true); // Skip API calls

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
        SellInteractor sellInteractor = new SellInteractor(stockDataAccessInterface, transactionDataAccessInterface,
                sellPresenter);
        sellInteractor.execute(buyInputData);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBTransactionDataAccessObject db = new DBTransactionDataAccessObject();
        db.hardDeleteAllFor("51", "NVDA");
    }
}
