package usecase.portfolio;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Interactor for the Portfolio Use Case.
 */
public class PortfolioInteractor implements PortfolioInputBoundary {
    private final PortfolioTransactionDataAccessInterface transactionDataAccessObject;
    private final PortfolioStockDataAccessInterface stockDataAccessObject;
    private final PortfolioOutputBoundary portfolioPresenter;

    public PortfolioInteractor(PortfolioTransactionDataAccessInterface transactionDataAccessInterface,
            PortfolioStockDataAccessInterface stockDataAccessInterface,
            PortfolioOutputBoundary portfolioPresenter) {
        this.transactionDataAccessObject = transactionDataAccessInterface;
        this.stockDataAccessObject = stockDataAccessInterface;
        this.portfolioPresenter = portfolioPresenter;
    }

    /**
     * Execute the Portofolio Use Case
     * 
     * @param portfolioInputData the input data.
     */
    @Override
    public void execute(PortfolioInputData portfolioInputData) {
        final String portfolioId = portfolioInputData.getPortfolioId();
        final String portfolioName = portfolioInputData.getPortfolioName();

        LinkedHashSet<String> tickers = new LinkedHashSet<>();
        LinkedHashMap<String, Double> values = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> amounts = new LinkedHashMap<>();

        String sql = """
                    SELECT ticker, quantity FROM holdings
                    WHERE portfolio_id = ? AND quantity > 0
                    ORDER BY ticker
                """;

        try (java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, portfolioId);
            java.sql.ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String ticker = rs.getString("ticker");
                int quantity = rs.getInt("quantity");
                tickers.add(ticker);
                amounts.put(ticker, quantity);
                double price = stockDataAccessObject.getPrice(ticker);
                values.put(ticker, price * quantity);
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error fetching holdings: " + e.getMessage());
        }

        double balance = dataaccess.DBPortfoliosDataAccessObject.fetchBalance(portfolioId);

        PortfolioOutputData outputData = new PortfolioOutputData(
                portfolioInputData.getUsername(),
                portfolioId,
                portfolioName,
                tickers.toArray(new String[0]),
                amounts.values().stream().mapToInt(Integer::intValue).toArray(),
                values.values().stream().mapToDouble(Double::doubleValue).toArray(),
                balance);
        portfolioPresenter.prepareView(outputData);
    }

    /**
     * Switch to the Buy View
     * 
     * @param portfolioId The portfolio id to update the state of the Buy View Model
     */
    @Override
    public void routeToBuy(String portfolioId) {
        portfolioPresenter.routeToBuy(portfolioId);
    }

    /**
     * Switch to the Sell View
     * 
     * @param portfolioId The portfolio id to update the state of the Sell View
     *                    Model
     */
    @Override
    public void routeToSell(String portfolioId) {
        portfolioPresenter.routeToSell(portfolioId);
    }
}