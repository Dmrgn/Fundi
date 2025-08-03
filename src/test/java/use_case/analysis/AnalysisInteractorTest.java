package use_case.analysis;

import data_access.DBTransactionDataAccessObject;
import data_access.DBStockDataAccessObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AnalysisInteractorTest {
    @BeforeAll
    static void setUp() {
        // Assuming portfolio hub test was already run
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
                assertEquals(10, analysisOutputData.numTickers);

            }

            @Override
            public void routeToPortfolio() {
                // Not testing
            }
        };
        AnalysisInteractor interactor = new AnalysisInteractor(dbStockDataAccessObject, dbTransactionDataAccessObject, analysisOutputBoundary);
        interactor.execute(analysisInputData);
    }

}
