package interface_adapter.sell;

import use_case.buy.BuyInputBoundary;
import use_case.buy.BuyInputData;
import use_case.sell.SellInputBoundary;
import use_case.sell.SellInputData;

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
