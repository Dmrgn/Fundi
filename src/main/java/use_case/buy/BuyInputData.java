package use_case.buy;

/**
 * The Input Data for the Buy Use Case.
 */
public class BuyInputData {
    String portfolioId;
    String ticker;
    int amount;

    public BuyInputData(String portfolioId, String ticker, int amount) {
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
