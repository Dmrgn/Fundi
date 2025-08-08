package interface_adapter.shortsell;

public class ShortState {
    private String portfolioId = "";
    private String error;
    private String ticker = "";
    private String amount = "";

    public String getPortfolioId() { return portfolioId; }
    public void setPortfolioId(String portfolioId) { this.portfolioId = portfolioId; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
}
