package use_case.sell;

import java.time.LocalDate;

import entity.Transaction;
import java.sql.DriverManager;

/**
 * Interactor for the Sell Use Case.
 */
public class SellInteractor implements SellInputBoundary {
    private final SellStockDataAccessInterface stockDataAccessInterface;
    private final SellTransactionDataAccessInterface transactionDataAccessInterface;
    private final SellOutputBoundary sellOutputBoundary;

    public SellInteractor(SellStockDataAccessInterface stockDataAccessInterface,
            SellTransactionDataAccessInterface transactionDataAccessInterface,
            SellOutputBoundary sellOutputBoundary) {
        this.stockDataAccessInterface = stockDataAccessInterface;
        this.transactionDataAccessInterface = transactionDataAccessInterface;
        this.sellOutputBoundary = sellOutputBoundary;
    }

    /**
     * Executes the Sell Use Case.
     * 
     * @param sellInputData the input data.
     */
    @Override
    public void execute(SellInputData sellInputData) {
        String portfolioId = sellInputData.getPortfolioId();
        String ticker = sellInputData.getTicker();
        int amount = sellInputData.getAmount();

        if (!stockDataAccessInterface.hasTicker(ticker)) {
            sellOutputBoundary.prepareFailView("Invalid ticker");
        }

        else {
            final double price = stockDataAccessInterface.getPrice(ticker);
            if (amount <= 0) {
                sellOutputBoundary.prepareFailView("Invalid amount");
            }

            // Check if user has enough quantity to sell
            else if (getCurrentHoldings(portfolioId, ticker) < amount) {
                sellOutputBoundary.prepareFailView("Insufficient quantity to sell");
            }

            else {
                // Save sell with NEGATIVE amount and POSITIVE price
                final Transaction transaction = new Transaction(
                        portfolioId,
                        ticker,
                        -amount, // changed: negative amount for sells
                        LocalDate.now(),
                        price // changed: positive price
                );

                transactionDataAccessInterface.save(transaction);

                sellOutputBoundary.prepareSuccessView(new SellOutputData(ticker, price, amount));
            }
        }
    }

    private int getCurrentHoldings(String portfolioId, String ticker) {
        String sql = """
                SELECT quantity FROM holdings
                WHERE portfolio_id = ? AND ticker = ?
                """;
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, portfolioId);
            pstmt.setString(2, ticker);
            java.sql.ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt("quantity") : 0;
        } catch (java.sql.SQLException e) {
            System.out.println("Error getting holdings: " + e.getMessage());
            return 0;
        }
    }
}
