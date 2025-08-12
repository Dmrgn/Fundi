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

        if (!stockDataAccessObject.hasTicker(ticker)) {
            buyPresenter.prepareFailView("Ticker is not available");
        }
        else if (amount <= 0) {
            buyPresenter.prepareFailView("Amount must be greater than 0");
        }
        else {
            final double price = stockDataAccessObject.getPrice(ticker);
            final LocalDate date = LocalDate.now();

            final Transaction transaction = new Transaction(
                    portfolioId,
                    ticker,
                    amount,
                    date,
                    price);

            transactionDataAccessObject.save(transaction);

            final BuyOutputData buyOutputData = new BuyOutputData(ticker, price, amount);
            buyPresenter.prepareSuccessView(buyOutputData);

            try {
                NotificationManager.getInstance().checkNewsAfterPurchase(ticker, amount);
            }
            catch (Exception ex) {
                System.err.println("Failed to check news notifications: " + ex.getMessage());
            }
        }
    }
}
