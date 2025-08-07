package use_case.analysis;

import data_access.DBTransactionDataAccessObject;
import data_access.DBStockDataAccessObject;
import entity.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AnalysisInteractorTest {
    @BeforeAll
    static void setUp() throws SQLException {
        DBTransactionDataAccessObject dbTransactionDataAccessObject = new DBTransactionDataAccessObject();
        dbTransactionDataAccessObject.save(new Transaction("51", "NVDA", 10,
                LocalDate.now(), 10.0));
    }

    @Test
    void analyze() throws SQLException {
        DBTransactionDataAccessObject dbTransactionDataAccessObject = new DBTransactionDataAccessObject();
        DBStockDataAccessObject dbStockDataAccessObject = new DBStockDataAccessObject();
        AnalysisInputData analysisInputData = new AnalysisInputData("51");
        AnalysisOutputBoundary analysisOutputBoundary = new AnalysisOutputBoundary() {
            @Override
            public void prepareView(AnalysisOutputData analysisOutputData) {
                assertNotNull(analysisOutputData.getMajorityTickers());
                assertNotNull(analysisOutputData.getMostVolTickers());
                assertNotNull(analysisOutputData.getTopReturns());
                assertNotNull(analysisOutputData.getWorstReturns());
                assertEquals(1, analysisOutputData.getMajorityTickers().size());
                assertEquals(10, analysisOutputData.getNumTickers());

            }

            @Override
            public void routeToPortfolio() {
                // Not testing
            }
        };
        AnalysisInteractor interactor = new AnalysisInteractor(dbStockDataAccessObject, dbTransactionDataAccessObject, analysisOutputBoundary);
        interactor.execute(analysisInputData);
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBTransactionDataAccessObject transactionDataAccessObject = new DBTransactionDataAccessObject();
        transactionDataAccessObject.remove("51", "NVDA", 10); // Update portfolio id accordingly

    }

}
