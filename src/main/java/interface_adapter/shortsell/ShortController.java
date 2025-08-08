package interface_adapter.shortsell;

import use_case.shortsell.ShortInputBoundary;
import use_case.shortsell.ShortInputData;

public class ShortController {
    private final ShortInputBoundary interactor;
    public ShortController(ShortInputBoundary interactor) { this.interactor = interactor; }
    public void execute(String portfolioId, String ticker, int amount) {
        interactor.execute(new ShortInputData(portfolioId, ticker, amount));
    }
}
