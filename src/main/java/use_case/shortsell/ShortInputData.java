package use_case.shortsell;

public class ShortInputData {
    private final String portfolioId;
    private final String ticker;
    private final int amount;

    public ShortInputData(String portfolioId, String ticker, int amount) {
        this.portfolioId = portfolioId;
        this.ticker = ticker;
        this.amount = amount;
    }

    public String getPortfolioId() { return portfolioId; }
    public String getTicker() { return ticker; }
    public int getAmount() { return amount; }
}
