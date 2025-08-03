package use_case.sell;

import entity.Transaction;

import java.time.LocalDate;

/**
 * Interactor for the Sell Use Case
 */
public class SellInteractor implements SellInputBoundary {
    SellStockDataAccessInterface stockDataAccessInterface;
    SellTransactionDataAccessInterface transactionDataAccessInterface;
    SellOutputBoundary sellOutputBoundary;

    public SellInteractor(SellStockDataAccessInterface stockDataAccessInterface,
                          SellTransactionDataAccessInterface transactionDataAccessInterface,
                          SellOutputBoundary sellOutputBoundary) {
        this.stockDataAccessInterface = stockDataAccessInterface;
        this.transactionDataAccessInterface = transactionDataAccessInterface;
        this.sellOutputBoundary = sellOutputBoundary;
    }


    /**
     * Executes the sell usecase.
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
            return;
        }
        final double price = stockDataAccessInterface.getPrice(ticker);
        if (amount < 0) {
            sellOutputBoundary.prepareFailView("Invalid amount");
        } else if (transactionDataAccessInterface.amountOfTicker(portfolioId, ticker) < amount) {
            sellOutputBoundary.prepareFailView("You do not have enough of this ticker");
        } else {
            final LocalDate date = LocalDate.now();
            transactionDataAccessInterface.save(new Transaction(
                    portfolioId,
                    ticker,
                    amount,
                    date,
                    -1 * price // Negative denotes sell
            ));
            sellOutputBoundary.prepareSuccessView(new SellOutputData(ticker, -1 * price, amount));
        }
    }
}
