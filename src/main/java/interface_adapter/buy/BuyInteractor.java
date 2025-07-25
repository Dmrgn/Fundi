package interface_adapter.buy;

import entity.Transaction;
import use_case.buy.*;

import java.time.LocalDate;

/**
 * The Login Interactor.
 */
public class BuyInteractor implements BuyInputBoundary {
    private final BuyStockDataAccessInterface stockDataAccessInterface;
    private final BuyTransactionDataAccessInterface buyTransactionDataAccessInterface;
    private final BuyOutputBoundary buyOutputBoundary;

    public BuyInteractor(BuyStockDataAccessInterface stockDataAccessInterface,
                         BuyTransactionDataAccessInterface buyTransactionDataAccessInterface,
                         BuyOutputBoundary buyOutputBoundary) {
        this.stockDataAccessInterface = stockDataAccessInterface;
        this.buyTransactionDataAccessInterface = buyTransactionDataAccessInterface;
        this.buyOutputBoundary = buyOutputBoundary;
    }

    @Override
    public void execute(BuyInputData buyInputData) {
        final String portfolioId = buyInputData.getPortfolioId();
        final String ticker = buyInputData.getTicker();
        final int amount = buyInputData.getAmount();

        if (!stockDataAccessInterface.hasTicker(ticker)) {
            buyOutputBoundary.prepareFailView("Ticker is not available");
        } else if (amount <= 0) {
            buyOutputBoundary.prepareFailView("Amount must be greater than 0");
        } else {
            final double price = stockDataAccessInterface.getPrice(ticker);
            final LocalDate date = LocalDate.now();

            Transaction transaction = new Transaction(portfolioId, ticker, amount, date, price);
            buyTransactionDataAccessInterface.save(transaction);
            buyOutputBoundary.prepareSuccessView(new BuyOutputData(

            ));
        }

    }
}