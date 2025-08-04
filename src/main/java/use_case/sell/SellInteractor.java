package use_case.sell;

import java.time.LocalDate;

import entity.Transaction;

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
            if (amount < 0) {
                sellOutputBoundary.prepareFailView("Invalid amount");
            }

            else if (transactionDataAccessInterface.amountOfTicker(portfolioId, ticker) < amount) {
                sellOutputBoundary.prepareFailView("You do not have enough of this ticker");
            }

            else {
                final LocalDate date = LocalDate.now();
                transactionDataAccessInterface.save(new Transaction(
                        portfolioId,
                        ticker,
                        amount,
                        date,
                        // Negative denotes sell
                        -1 * price
                ));
                sellOutputBoundary.prepareSuccessView(new SellOutputData(ticker, -1 * price, amount));
            }
        }
    }
}
