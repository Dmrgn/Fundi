package use_case.portfolios;

import data_access.DBPortfoliosDataAccessObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PortfoliosInteractorTest {

    @Test
    public void successTest() throws SQLException {
        PortfoliosDataAccessInterface dataAccessInterface = new DBPortfoliosDataAccessObject();
        String username = "Paul";
        Map<String, String> portfolios = new HashMap<>();
        portfolios.put("testPortfolio", "68"); // update as necessary

        PortfoliosOutputBoundary portfoliosOutputBoundary = new PortfoliosOutputBoundary() {
            @Override
            public void prepareView(PortfoliosOutputData outputData) {
                assertEquals(portfolios, outputData.getPortfolios());
                assertEquals(username, outputData.getUsername());
            }

            @Override
            public void routeToCreate(String username) {
                // Not using this
            }
        };
        PortfoliosInteractor portfoliosInteractor = new PortfoliosInteractor(portfoliosOutputBoundary, dataAccessInterface);
        portfoliosInteractor.execute(new PortfoliosInputData(username));
    }
}
