package interface_adapter.buy;

import use_case.buy.BuyInputBoundary;
import use_case.buy.BuyInputData;

/**
 * The controller for the Buy Use Case.
 */
public class BuyController {

    private final BuyInputBoundary buyUseCaseInteractor;

    public BuyController(BuyInputBoundary buyUseCaseInteractor) {
        this.buyUseCaseInteractor = buyUseCaseInteractor;
    }

    /**
     * Executes the buy Use Case.
     * @param portfolioId the portfolio id
     * @param ticker the stock ticker
     * @param amount the amount of stock to buy
     */
    public void execute(String portfolioId, String ticker, int amount) {
        final BuyInputData buyInputData = new BuyInputData(portfolioId, ticker, amount);
        buyUseCaseInteractor.execute(buyInputData);
    }
}