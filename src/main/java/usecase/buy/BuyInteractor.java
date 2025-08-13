package usecase.buy;

import java.time.LocalDate;

import entity.Transaction;
import usecase.notifications.NotificationManager;

/**
 * Interactor for the Buy Use Case.
 */
public class BuyInteractor implements BuyInputBoundary {
    private final BuyStockDataAccessInterface stockDataAccessObject;
    private final BuyTransactionDataAccessInterface transactionDataAccessObject;
    private final BuyOutputBoundary buyPresenter;

    public BuyInteractor(BuyStockDataAccessInterface stockDataAccessInterface,
            BuyTransactionDataAccessInterface transactionDataAccessInterface,
            BuyOutputBoundary buyOutputBoundary) {
        this.stockDataAccessObject = stockDataAccessInterface;
        this.transactionDataAccessObject = transactionDataAccessInterface;
        this.buyPresenter = buyOutputBoundary;
    }

    /**
     * Executes the Buy Use Case.
     * 
     * @param buyInputData the input data.
     */

    @Override
    public void execute(BuyInputData buyInputData) {
        final String portfolioId = buyInputData.getPortfolioId();
        final String ticker = buyInputData.getTicker();
        final int amount = buyInputData.getAmount();

        // Validate inputs
        if (!stockDataAccessObject.hasTicker(ticker)) {
            buyPresenter.prepareFailView("Ticker is not available");
            return;
        }
        if (amount <= 0) {
            buyPresenter.prepareFailView("Amount must be greater than 0");
            return;
        }

        // Get current stock price
        final double currentPrice = stockDataAccessObject.getPrice(ticker);
        final double totalCost = currentPrice * amount;

        // Check current balance
        final double currentBalance = transactionDataAccessObject.getPortfolioBalance(portfolioId);
        System.out.println("Current balance for portfolio " + portfolioId + ": " + currentBalance);

        // Check if user has enough funds
        if (currentBalance < totalCost) {
            buyPresenter.prepareFailView("Not enough funds");
            return;
        }

        // Proceed with purchase
        final LocalDate date = LocalDate.now();
        final Transaction transaction = new Transaction(portfolioId, ticker, amount, date, currentPrice);
        transactionDataAccessObject.save(transaction);

        // Update balance (deduct cost)
        final double newBalance = currentBalance - totalCost;
        System.out.println(
                "Updating balance from " + currentBalance + " to " + newBalance + " (cost: " + totalCost + ")");
        transactionDataAccessObject.updatePortfolioBalance(portfolioId, newBalance);

        final BuyOutputData buyOutputData = new BuyOutputData(
                ticker, currentPrice, amount, newBalance, true, "Purchase successful");
        buyPresenter.prepareSuccessView(buyOutputData);

        try {
            NotificationManager.getInstance().checkNewsAfterPurchase(ticker, amount);
        } catch (Exception ex) {
            System.err.println("Failed to check news notifications: " + ex.getMessage());
        }
    }
}
