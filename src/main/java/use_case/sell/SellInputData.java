package use_case.sell;

/**
 * The Input Data for the Sell Use Case.
 */
public class SellInputData {
    String portfolioId;
    String ticker;
    int amount;

    public SellInputData(String portfolioId, String ticker, int amount) {
        this.portfolioId = portfolioId;
        this.ticker = ticker;
        this.amount = amount;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getTicker() {
        return ticker;
    }

    public int getAmount() {
        return amount;
    }
}
