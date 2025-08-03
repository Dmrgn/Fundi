package interface_adapter.sell;

/**
 * The state for the Sell View Model
 */
public class SellState {
    private String portfolioId = "";
    private String sellError;

    public SellState(SellState copy) {
        this.portfolioId = copy.getPortfolioId();
        this.sellError = copy.getSellError();
    }

    public SellState() {

    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getSellError() {
        return sellError;
    }

    public void setSellError(String sellError) {
        this.sellError = sellError;
    }

}
