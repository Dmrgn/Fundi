package use_case.portfolioHub;

import data_access.DBPortfoliosDataAccessObject;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PortfolioHubInteractorTest {

    @Test
    public void successTest() throws SQLException {
        PortfolioHubDataAccessInterface dataAccessInterface = new DBPortfoliosDataAccessObject();
        String username = "Paul";
        Map<String, String> portfolios = new HashMap<>();
        portfolios.put("testPortfolio", "68"); // update as necessary

        PortfolioHubOutputBoundary portfoliosOutputBoundary = new PortfolioHubOutputBoundary() {
            @Override
            public void prepareView(PortfolioHubOutputData outputData) {
                assertEquals(portfolios, outputData.getPortfolios());
                assertEquals(username, outputData.getUsername());
            }

            @Override
            public void routeToCreate(String username) {
                // Not using this
            }
        };
        PortfolioHubInteractor portfoliosInteractor = new PortfolioHubInteractor(portfoliosOutputBoundary, dataAccessInterface);
        portfoliosInteractor.execute(new PortfolioHubInputData(username));
    }
}
