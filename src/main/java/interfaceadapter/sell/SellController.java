package interfaceadapter.sell;

import usecase.sell.SellInputBoundary;
import usecase.sell.SellInputData;

/**
 * The Controller for the Sell Use Case.
 */
public class SellController {
    private final SellInputBoundary sellUseCaseInteractor;

    public SellController(SellInputBoundary sellUseCaseInteractor) {
        this.sellUseCaseInteractor = sellUseCaseInteractor;
    }

    /**
     * Executes the buy Use Case.
     * @param portfolioId the portfolio id
     * @param ticker the stock ticker
     * @param amount the amount of stock to buy
     */
    public void execute(String portfolioId, String ticker, int amount) {
        final SellInputData sellInputData = new SellInputData(portfolioId, ticker, amount);
        sellUseCaseInteractor.execute(sellInputData);
    }
}
