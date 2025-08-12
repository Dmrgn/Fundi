package usecase.recommend;

import dataaccess.DBTransactionDataAccessObject;
import dataaccess.DBStockDataAccessObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RecommendInteractorTest {
    @BeforeAll
    static void setUp() {
        // Assuming buy and sell tests were already run
    }

    @Test
    void recommendTest() throws SQLException {
        DBTransactionDataAccessObject dbTransactionDataAccessObject = new DBTransactionDataAccessObject();
        DBStockDataAccessObject dbStockDataAccessObject = new DBStockDataAccessObject();

        RecommendInputData recommendInputData = new RecommendInputData("51");
        RecommendOutputBoundary presenter = new RecommendOutputBoundary() {
            @Override
            public void prepareView(RecommendOutputData recommendOutputData) {
                assertNotNull(recommendOutputData.getHaveRecs(), "Have recommendations should not be null");
                assertNotNull(recommendOutputData.getNotHaveRecs(), "Not-have recommendations should not be null");
                assertNotNull(recommendOutputData.getSafeRecs(), "Safe recommendations should not be null");

                assertTrue(recommendOutputData.getSafeRecs().size() <= 3, "Have recs list too large");
                assertTrue(recommendOutputData.getNotHaveRecs().size() <= 3, "Not-have recs list too large");
                assertTrue(recommendOutputData.getSafeRecs().size() <= 3, "Safe recs list too large");
            }

            @Override
            public void routeToPortfolio() {
                // Not testing this
            }
        };
        RecommendInteractor interactor = new RecommendInteractor(dbStockDataAccessObject, dbTransactionDataAccessObject, presenter);
        interactor.execute(recommendInputData);
    }
}
