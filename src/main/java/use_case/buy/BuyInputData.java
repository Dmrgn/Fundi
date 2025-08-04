package use_case.buy;

/**
 * The Input Data for the Buy Use Case.
 */
public class BuyInputData {
    private final String portfolioId;
    private final String ticker;
    private final int amount;

    public BuyInputData(String portfolioId, String ticker, int amount) {
        this.portfolioId = portfolioId;
        this.ticker = ticker;
        this.amount = amount;
    }

    /**
     * Getter.
     * @return Portfolio Id
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * Getter.
     * @return Ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Getter.
     * @return Amount
     */
    public int getAmount() {
        return amount;
    }
}
