package interface_adapter.buy;

public class BuyState {
    private String portfolioId = "";
    private String buyError = "";

    public BuyState(BuyState copy) {
        this.portfolioId = copy.getPortfolioId();
        this.buyError = copy.getBuyError();
    }

    public BuyState() {

    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getBuyError() {
        return buyError;
    }

    public void setBuyError(String buyError) {
        this.buyError = buyError;
    }

}
